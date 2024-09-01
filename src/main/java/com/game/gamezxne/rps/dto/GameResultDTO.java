package com.game.gamezxne.rps.dto;

import com.game.gamezxne.rps.model.GameModel;
import com.game.gamezxne.rps.model.PlayerModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResultDTO {
    
    private PlayerModel winner;
    private GameModel game;

}
