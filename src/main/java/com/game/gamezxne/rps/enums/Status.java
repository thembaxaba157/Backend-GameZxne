package com.game.gamezxne.rps.enums;



public class Status{
    public enum GameStatus {

        IN_PROGRESS, //ROUND STILL IN PROGRESS
        GAME_OVER, // Game is over
        DEATH_ROUNDS,
        WAITING_FOR_PLAYERS; // Waiting for people to connect

       

    }

    public enum PlayerGameStatus {
        WIN,
        LOSE,
        DRAW,
        WAITING_FOR_GAME_START,
        IN_PROGRESS
    
    }


    public enum PlayerMoveStatus {
        NOTPICKED,
        PICKED,
    }

   

}


