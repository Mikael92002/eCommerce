package com.mikael.eCommerce.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Use ResponseStatusException as "custom http codes":
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        CustomErrorResponse customError = new CustomErrorResponse(e.getStatusCode().value(), e.getReason());
        return ResponseEntity.status(e.getStatusCode()).body(customError);
    }

    // Use as all other uncaught spring-related http errors:
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<CustomErrorResponse> handleHTTPExceptions(ErrorResponseException e){
        CustomErrorResponse customError = new CustomErrorResponse(e.getStatusCode().value(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(customError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationErrors(AuthenticationException e){
        CustomErrorResponse customError = new CustomErrorResponse(401, e.getMessage());
        return ResponseEntity.status(401).body(customError);
    }

//    // catch-all:
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomErrorResponse> handleAllExceptions(Exception e){
//        // generic 500:
//        CustomErrorResponse customError = new CustomErrorResponse(500,e.getMessage());
//        return ResponseEntity.status(500).body(customError);
//    }
}
