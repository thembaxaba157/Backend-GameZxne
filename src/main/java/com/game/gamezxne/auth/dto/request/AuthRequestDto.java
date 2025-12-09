package com.game.gamezxne.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {

        @NotBlank(message = "Enter Valid Username")
        private String username;
        @NotBlank(message = "Enter Valid Password")
        private String password;
    }
    

