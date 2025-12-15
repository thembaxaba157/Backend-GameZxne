package com.game.gamezxne.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
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

    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req){ 

        ApiError error = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(),
        "ResourceNotFound", req.getRequestURI());
        
        ApiResponse<ApiError> response = new ApiResponse<ApiError>(false, error,ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleResourceAlreadyExists(ResourceAlreadyExistException ex, 
        HttpServletRequest req){


             Map<String, String> details = new HashMap<>();
        details.put(ex.getField(), ex.getMessage());
       
        ApiError error = new ApiError(Instant.now(), HttpStatus.CONFLICT.value(),
        "ResourceAlreadyExists",req.getRequestURI(),details);
        

        ApiResponse<ApiError> response = new ApiResponse<ApiError>(false, error, "Conflict"); 
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

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


    // this a fail safe exception for when two users enter the same data that is supposed to be unique at the same it was supposed to be for checking if username, email etc exists but howver the info that we get fromt the exception it doesnt allow us to customize the error message the field that cause the error so we just had to generalize the response for this
    @ExceptionHandler(DataIntegrityViolationException.class)    
    public ResponseEntity<ApiResponse<ApiError>> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest req){
       

        ApiError error = new ApiError(Instant.now(), HttpStatus.CONFLICT.value(),"Data Integrity Violation" , req.getRequestURI());
        ApiResponse<ApiError> response = new ApiResponse<ApiError>(false, error, "A resource with the same unique identifier");
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }


}
