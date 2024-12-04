package com.game.gamezxne.rps.dto;

import lombok.Data;

@Data
public class PlayerScoreDTO {

    String username;
    int score;

    public PlayerScoreDTO(String username, int score){
        this.username = username;
        this.score = score;
    }

    
}
