package com.game.gamezxne.rps.dto;

import com.game.gamezxne.rps.enums.Status.PlayerMoveStatus;

import lombok.Data;

@Data
public class PlayerStateDTO {

    private String username;
    private Long id;
    private int score;
    private PlayerMoveStatus playerStatus;


}
