package com.game.rps.exceptions;

public class PlayerNotFoundException extends RuntimeException{

    
    public PlayerNotFoundException(String message){

        super(message);
    }
}
