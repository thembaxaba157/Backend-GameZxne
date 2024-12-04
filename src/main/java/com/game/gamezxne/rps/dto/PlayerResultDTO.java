package com.game.gamezxne.rps.dto;

import com.game.gamezxne.rps.enums.Status.PlayerGameStatus;

import lombok.Data;

@Data
public class PlayerResultDTO {
    
    private String username;
    private int score;
    private int round;
    private PlayerGameStatus result;

}
