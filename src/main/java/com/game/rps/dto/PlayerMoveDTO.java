package com.game.rps.dto;

import com.game.rps.enums.Move;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class PlayerMoveDTO {
    
    Long game_id;
    
    @Enumerated(EnumType.STRING)
    Move move;

    Long player_id;
    
}
