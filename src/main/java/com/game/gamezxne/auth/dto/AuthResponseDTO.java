package com.game.gamezxne.auth.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    
    private UserReponseDto user;
    private String userSessionToken;

    public AuthResponseDTO(UserReponseDto user, String userSessionToken){
        this.user = user;
        this.userSessionToken = userSessionToken;
    }

}
