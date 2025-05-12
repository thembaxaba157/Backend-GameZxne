package com.game.gamezxne.rps.dto;

import lombok.Data;

@Data
public class CreateGameDTO {

    private String lobbyName;
    private int numberOfRounds;
    private int playerId;
    private int numberOfPlayers;
   
    
}
