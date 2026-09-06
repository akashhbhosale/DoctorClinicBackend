package com.doctorclinicapp.backend.security;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Runs once per request. Reads the Authorization header, validates the JWT,
 * and (if valid) puts an Authentication into the SecurityContext so
 * SecurityConfig's .authenticated() rules actually have something to check.
 *
 * If there's no token, or it's invalid/expired, we simply don't set an
 * Authentication and let the request continue — SecurityConfig's
 * ExceptionTranslationFilter will turn "no authentication" into a 401
 * for any endpoint that requires auth. Public endpoints (/api/auth/**)
 * work fine either way since they're permitAll().
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtService.isTokenValid(token, username)) {

                    String role = jwtService.extractClaim(
                            token,
                            claims -> claims.get("role", String.class)
                    );
                    if (role == null) {
                        role = "DOCTOR";
                    }

                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                    var authToken = new UsernamePasswordAuthenticationToken(
                            username, null, authorities
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (JwtException | IllegalArgumentException ex) {
            // Invalid/expired/malformed token: don't authenticate.
            // Downstream .authenticated() rules will reject with 401.
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
