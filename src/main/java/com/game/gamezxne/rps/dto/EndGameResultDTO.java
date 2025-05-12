package com.game.gamezxne.rps.dto;

import java.util.List;

import com.game.gamezxne.rps.enums.Status.GameStatus;
import com.game.gamezxne.rps.enums.Status.PlayerGameStatus;

import lombok.Data;

@Data
public class EndGameResultDTO {
    
    RoundResultDTO roundResult;
    GameStatus gameStatus;
    List<PlayerFinalOutcomeDTO> playersFinalOutcomes;


    public void addPlayerFinalOutcome(String username, PlayerGameStatus playerOutcome){
        playersFinalOutcomes.add(new PlayerFinalOutcomeDTO(username, playerOutcome));
    }
}
