package com.game.gamezxne.rps.dto;


import java.util.List;

import lombok.Data;

@Data
public class RoundResultDTO {
    int round;
    List<PlayerScoreDTO> playerScore;

    public void addPlayerScore(String username, int score){
        playerScore.add(new PlayerScoreDTO(username, score));
    }
    

}
