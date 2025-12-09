package com.game.gamezxne.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min=6,max=18,message = "Password must be 6 to 18 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter Valid Email")
    private String email;
    
}
