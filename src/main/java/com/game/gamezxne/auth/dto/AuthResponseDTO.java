package com.game.gamezxne.auth.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    
    private UserReponseDto userReponseDto;
    private String userSessionToken;

}
