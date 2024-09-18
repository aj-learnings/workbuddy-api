package com.ajlearnings.workbuddy.exception;

import com.ajlearnings.workbuddy.model.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleGeneralValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var errorResponse = ErrorResponse.builder()
                                        .guid(guid)
                                        .message(exception.getMessage())
                                        .statusCode(HttpStatus.NOT_FOUND.value())
                                        .statusName(HttpStatus.NOT_FOUND.name())
                                        .path(request.getRequestURI())
                                        .method(request.getMethod())
                                        .timestamp(LocalDateTime.now().toString())
                                        .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var errorResponse = ErrorResponse.builder()
                .guid(guid)
                .message(exception.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .statusName(HttpStatus.CONFLICT.name())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(Exception exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var errorResponse = ErrorResponse.builder()
                                            .guid(guid)
                                            .message(exception.getMessage())
                                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                                            .statusName(HttpStatus.UNAUTHORIZED.name())
                                            .path(request.getRequestURI())
                                            .method(request.getMethod())
                                            .timestamp(LocalDateTime.now().toString())
                                            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(Exception exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var errorResponse = ErrorResponse.builder()
                .guid(guid)
                .message(exception.getMessage())
                .statusCode(HttpStatus.FORBIDDEN.value())
                .statusName(HttpStatus.FORBIDDEN.name())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(Exception exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var errorResponse = ErrorResponse.builder()
                .guid(guid)
                .message("Invalid jwt token")
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .statusName(HttpStatus.UNAUTHORIZED.name())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(
                String.format("Error GUID=%s; error message: %s", guid, exception.getMessage()),
                exception
        );
        var errorResponse = ErrorResponse.builder()
                                        .guid(guid)
                                        .message(exception.getMessage())
                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .statusName(HttpStatus.INTERNAL_SERVER_ERROR.name())
                                        .path(request.getRequestURI())
                                        .method(request.getMethod())
                                        .timestamp(LocalDateTime.now().toString())
                                        .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
