package com.workintech.spring17challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiError(ApiException apiException) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(apiException.getStatus().value(), apiException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, apiException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiError(Exception exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
