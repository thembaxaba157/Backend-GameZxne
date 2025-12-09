package com.game.gamezxne.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.gamezxne.auth.dto.request.AuthRequestDto;
import com.game.gamezxne.auth.dto.request.RegisterRequestDto;
import com.game.gamezxne.auth.dto.response.AuthResponseDTO;
import com.game.gamezxne.auth.dto.response.UserResponseDto;
import com.game.gamezxne.auth.service.AuthService;
import com.game.gamezxne.protocol.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody @Valid RegisterRequestDto registrationDetails) {
        AuthResponseDTO authResponseDTO = authService.registerUser(registrationDetails);
      
        ApiResponse<AuthResponseDTO> apiResponse = new ApiResponse<>(true, authResponseDTO,"User successully registered and logged in");

        return ResponseEntity.ok().body(apiResponse);
        
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody AuthRequestDto authRequestDto){
        AuthResponseDTO authResponseDTO = authService.loginUser(authRequestDto);
        ApiResponse<AuthResponseDTO> apiResponse = new ApiResponse<AuthResponseDTO>(false, authResponseDTO, "User successfully logged in");

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUsers(){
        List<UserResponseDto> users = authService.getUsers();

        String message = users.isEmpty() ? "No users found" : "Success";
        ApiResponse<List<UserResponseDto>> apiResponse = new ApiResponse<List<UserResponseDto>>(true, users,message);

        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> getUser(@PathVariable Long id){

        UserResponseDto user = authService.getUserbyId(id);
        ApiResponse<UserResponseDto> apiResponse = new ApiResponse<UserResponseDto>(false, user);

        return ResponseEntity.ok().body(apiResponse);
    }

}
