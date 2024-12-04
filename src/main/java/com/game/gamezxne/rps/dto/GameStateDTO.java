package com.game.gamezxne.rps.dto;



import com.game.gamezxne.rps.enums.Status.GameStatus;

import lombok.Data;

@Data
public class GameStateDTO {

    GameStatus gameStatus;
    int currRound;
    int maxNumberRounds;
    RoundResultDTO roundResult;

}
