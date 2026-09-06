package com.doctorclinicapp.backend.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.doctorclinicapp.backend.exception.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Replaces Spring Security's default 401 (a whitelabel/blank response) with
 * the same ApiErrorResponse JSON shape the rest of the API already uses.
 * Triggered whenever an unauthenticated request hits a protected endpoint.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(401)
                .error("Unauthorized")
                .message("Missing or invalid authentication token")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
