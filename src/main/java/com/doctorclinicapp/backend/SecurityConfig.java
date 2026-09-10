package com.doctorclinicapp.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.doctorclinicapp.backend.security.JwtAuthenticationEntryPoint;
import com.doctorclinicapp.backend.security.JwtAuthenticationFilter;
import com.doctorclinicapp.backend.security.RoleAccessDeniedHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize("hasRole('...')") on controller methods
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final RoleAccessDeniedHandler roleAccessDeniedHandler;

	// Password Encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Securityfilterchain  function
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				// Stateless: every request must carry its own valid JWT, no server-side session.
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.accessDeniedHandler(roleAccessDeniedHandler)
				)
				.authorizeHttpRequests(auth -> auth
						// Only login/register are public. /api/auth/admin/register requires
						// ADMIN (enforced by @PreAuthorize on that method) — deliberately
						// NOT included here even though it's under /api/auth.
						.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
						// Actuator health check for deployment platforms (Railway etc.)
						.requestMatchers("/actuator/health").permitAll()
						// Everything else requires a valid JWT.
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// Configurations
	@Bean
	public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
		var cfg = new org.springframework.web.cors.CorsConfiguration();
		cfg.setAllowedOriginPatterns(java.util.List.of("http://localhost:5173", "https://*.vercel.app"
		// or replace with your exact domain, e.g.
		// "https://doctor-clinic-frontend.vercel.app"
		));
		cfg.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		cfg.setAllowedHeaders(java.util.List.of("*"));
		cfg.setAllowCredentials(true);

		var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", cfg);
		return source;
	}

}
