package com.doctorclinicapp.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  // <-- add this bean
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .cors(Customizer.withDefaults())
      // keep everything open while we finish auth
      .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

    return http.build();
  }
  
  @Bean
  public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
      var cfg = new org.springframework.web.cors.CorsConfiguration();
      cfg.setAllowedOrigins(java.util.List.of("http://localhost:5173")); // React dev server
      cfg.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
      cfg.setAllowedHeaders(java.util.List.of("*"));
      cfg.setAllowCredentials(true);

      var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", cfg);
      return source;
  }

}
