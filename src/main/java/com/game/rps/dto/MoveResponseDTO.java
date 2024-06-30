package com.game.rps.dto;

import com.game.rps.enums.MoveResponseMessage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoveResponseDTO {
    
    Long playerId;
    Long game_id;
    MoveResponseMessage moveResponseMessage;

}
