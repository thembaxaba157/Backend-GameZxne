package com.game.gamezxne.rps.dto;

import com.game.gamezxne.rps.enums.Status.PlayerGameStatus;

import lombok.Data;

@Data
public class PlayerFinalOutcomeDTO {

    String username;
    PlayerGameStatus playerFinalResult;

    public PlayerFinalOutcomeDTO(String username, PlayerGameStatus playerFinalResult){
        this.username = username;
        this.playerFinalResult = playerFinalResult;
    }

}
