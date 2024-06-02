package com.game.rps.dto;

import com.game.rps.enums.MoveResponseMessage;

import lombok.Setter;

@Setter
public class MoveResponseDTO {
    
    Long playerId;
    Long game_id;
    MoveResponseMessage moveResponseMessage;

}
