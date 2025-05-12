package com.game.gamezxne.rps.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PlayerDTO {
    
    private Long id;
    private String username;

}
