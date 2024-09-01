package com.game.gamezxne.rps.dto;

import com.game.gamezxne.rps.enums.MoveResponseMessage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoveResponseDTO {
    
    Long playerId;
    Long game_id;
    MoveResponseMessage moveResponseMessage;

}
