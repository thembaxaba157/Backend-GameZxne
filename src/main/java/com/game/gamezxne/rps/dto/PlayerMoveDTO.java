package com.game.gamezxne.rps.dto;

import com.game.gamezxne.rps.enums.Move;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class PlayerMoveDTO {
    
    Long game_id;
    
    @Enumerated(EnumType.STRING)
    Move move;
    
}
