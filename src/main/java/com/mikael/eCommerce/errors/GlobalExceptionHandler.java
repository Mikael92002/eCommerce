package com.mikael.eCommerce.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e){
        CustomErrorResponse customError = new CustomErrorResponse(e.getStatusCode().value(), e.getReason());
        return ResponseEntity.status(e.getStatusCode()).body(customError);
    }
}
