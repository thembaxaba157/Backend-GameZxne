package com.game.rps.enums;



public class Status{
    public enum GameStatus {

        IN_PROGRESS, //ROUND STILL IN PROGRESS
        GAME_OVER, // Game is over
        WAITING_FOR_PLAYERS // Waiting for people to connect
    }

    public enum PlayerStatus {
        NOTPICKED,
        PICKED,
    }

}


