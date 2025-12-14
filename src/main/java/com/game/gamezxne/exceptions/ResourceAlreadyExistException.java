package com.game.gamezxne.exceptions;

import lombok.Getter;

public class ResourceAlreadyExistException extends RuntimeException{


    @Getter
    String field;
    
    public ResourceAlreadyExistException(String field, String message){
        super(message);
        this.field = field;
    }
    
}
