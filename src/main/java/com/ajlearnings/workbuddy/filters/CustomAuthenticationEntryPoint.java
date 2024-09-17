package com.ajlearnings.workbuddy.filters;

import com.ajlearnings.workbuddy.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        var guid = UUID.randomUUID().toString();
        var errorResponse = ErrorResponse.builder()
                                        .guid(guid)
                                        .message("You are not authenticated")
                                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                                        .statusName(HttpStatus.UNAUTHORIZED.name())
                                        .path(request.getRequestURI())
                                        .method(request.getMethod())
                                        .build();
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}

