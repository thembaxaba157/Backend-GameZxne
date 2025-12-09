package com.game.gamezxne.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {

        @NotBlank(message = "Enter Valid Username")
        private String username;
        @NotBlank(message = "Enter Valid Password")
        private String password;
    }
    

