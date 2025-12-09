package com.game.gamezxne.auth.dto;

import lombok.Data;

@Data
public class UserReponseDto {

    private Long id;
    private String username;
    private String email;

    public UserReponseDto(Long id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }

}