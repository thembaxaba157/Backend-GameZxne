package com.game.gamezxne.auth.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;

    public UserResponseDto(Long id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

}