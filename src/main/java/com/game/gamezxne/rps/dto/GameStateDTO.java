package com.game.gamezxne.rps.dto;

import java.util.List;

import lombok.Data;

@Data
public class GameStateDTO {
    private Long id;
    private String lobbyName;
    private List<PlayerStateDTO> currentPlayers;
    private String lobbyOwner;

}
