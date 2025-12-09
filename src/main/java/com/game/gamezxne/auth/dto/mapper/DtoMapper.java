package com.game.gamezxne.auth.dto.mapper;

import com.game.gamezxne.auth.dto.response.UserResponseDto;
import com.game.gamezxne.auth.model.UserModel;

public class DtoMapper {

    public static UserResponseDto toUserResponseDto(UserModel user){
        return new UserResponseDto(user.getId(), user.getUsername(),user.getEmail());
    }
    
}
