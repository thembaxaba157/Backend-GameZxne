package com.game.gamezxne.auth.dto.response;

import lombok.Data;

@Data
public class AuthResponseDTO {
    
    private UserResponseDto user;
    private String userSessionToken;

    public AuthResponseDTO(UserResponseDto user, String userSessionToken){
        this.user = user;
        this.userSessionToken = userSessionToken;
    }

}
