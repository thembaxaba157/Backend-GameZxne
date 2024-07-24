package com.game.rps.dto;

import com.game.rps.model.GameModel;
import com.game.rps.model.PlayerModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResultDTO {
    
    private PlayerModel winner;
    private GameModel game;

}
