package com.game.rps.exceptions;

public class GameNotFoundException extends RuntimeException {

    
    public GameNotFoundException(String message){
        super(message);
    }
}