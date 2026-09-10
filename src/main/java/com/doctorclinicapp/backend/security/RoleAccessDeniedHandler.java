package com.doctorclinicapp.backend.security;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.doctorclinicapp.backend.exception.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Fires when an authenticated user is logged in fine, but their role doesn't
 * have permission for the endpoint (e.g. a NURSE hitting a doctor-only
 * @PreAuthorize-protected diagnosis/procedure endpoint, or a non-ADMIN
 * hitting patient delete/restore). Returns the same ApiErrorResponse JSON
 * shape as the rest of the API instead of Spring's default blank 403.
 */
@Component
public class RoleAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(403)
                .error("Forbidden")
                .message("Your role does not have permission to perform this action")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
