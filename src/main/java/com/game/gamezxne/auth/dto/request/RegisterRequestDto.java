package com.game.gamezxne.auth.dto.request;


import com.game.gamezxne.auth.validator.password.ValidPassword;
import com.game.gamezxne.auth.validator.username.ValidUsername;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDto {
    
    // @NotBlank(message = "Username is required")
    @ValidUsername
    private String username;

    // @Size(min=6,max=18,message = "Password must be 6 to 18 characters")
    @ValidPassword
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter Valid Email")
    private String email;
    
}
