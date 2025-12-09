package com.game.gamezxne.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.game.gamezxne.protocol.ApiError;
import com.game.gamezxne.protocol.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponse<ApiError>> handleResourceNotFound(ResourceNotFound ex, HttpServletRequest req){ 

        ApiError error = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(),
        "ResourceNotFound", req.getRequestURI());
        
        ApiResponse<ApiError> response = new ApiResponse<ApiError>(false, error,ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest req){

        Map<String, String> details = new HashMap<>();

        ex.getBindingResult().getFieldErrors();
        for(FieldError error: ex.getBindingResult().getFieldErrors()){
            details.put(error.getField(), error.getDefaultMessage());
        }

        ApiError error = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "MethodArgumentNotValid", req.getRequestURI(),details);
        ApiResponse<ApiError> response = new ApiResponse<ApiError>(false, error,"Validation Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);        
    }


}
