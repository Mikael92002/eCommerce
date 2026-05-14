package com.mikael.eCommerce.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Use ResponseStatusException as "custom http codes":
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        log.error("ResponseStatusException: [{}]: {}", e.getStatusCode(), e.getReason());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getStatusCode().value(), e.getReason());
        return ResponseEntity.status(e.getStatusCode()).body(customErrorResponse);
    }

    // use for Bad jwt token/ unauthorized:
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<CustomErrorResponse> handleAuthenticationExceptions(BadCredentialsException e) {
        log.error("BadCredentialsException: {}", e.getMessage());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(400, e.getMessage());
        return ResponseEntity.status(401).body(customErrorResponse);
    }

    // validation errors:
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String fieldName = error.getField(); // e.g. "email", "password"
                    String message = error.getDefaultMessage(); // e.g. username too short
                    errors.put(fieldName, message);
                });
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(e.getStatusCode().value(), errors);
        return ResponseEntity.status(e.getStatusCode()).body(validationErrorResponse);
    }

    // MOST non-custom http errors:
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<CustomErrorResponse> handleHTTPErrors(ErrorResponseException e){
        log.error("HTTP error: [{}]: {}", e.getStatusCode(), e.getMessage());
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getStatusCode().value(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(customErrorResponse);
    }

    // catch-all:
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllExceptions(Exception e) {
        // generic 500:
        log.error("Unhandled/catch-all Exception: ", e);
        CustomErrorResponse customError = new CustomErrorResponse(500, "Internal server error");
        return ResponseEntity.status(500).body(customError);
    }
}
