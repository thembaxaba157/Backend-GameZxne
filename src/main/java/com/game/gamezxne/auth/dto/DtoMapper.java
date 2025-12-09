package com.game.gamezxne.auth.dto;

import com.game.gamezxne.auth.model.UserModel;

public class DtoMapper {

    public static UserReponseDto toUserResponseDto(UserModel user){
        return new UserReponseDto(user.getId(), user.getUsername(),user.getEmail());
    }
    
}
