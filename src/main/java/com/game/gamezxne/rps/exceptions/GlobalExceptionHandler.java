package com.game.gamezxne.rps.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePlayerNotFoundException(PlayerNotFoundException ex, WebRequest webRequest){
        ErrorObject errorObject = new ErrorObject();
        
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorObject> handleGameNotFoundException(GameNotFoundException ex, WebRequest webRequest){

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

}
