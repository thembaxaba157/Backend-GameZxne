package com.game.gamezxne.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.gamezxne.auth.dto.AuthRequestDto;
import com.game.gamezxne.auth.dto.AuthResponseDTO;
import com.game.gamezxne.auth.dto.RegisterUserDto;
import com.game.gamezxne.auth.service.AuthService;
import com.game.gamezxne.protocol.ApiResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    // @Autowired
    // public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
    //                       JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository) {
    //     this.authenticationManager = authenticationManager;
    //     this.userDetailsService = userDetailsService;
    //     this.jwtTokenProvider = jwtTokenProvider;
    //     this.passwordEncoder = passwordEncoder;
    //     this.userRepository = userRepository;
    // }
    
    
    // @PostMapping("/login")
    // public String login(@RequestBody AuthRequestDto authRequest) throws Exception {
    //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    //     final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
    //     return jwtTokenProvider.generateToken(userDetails);
    // }

    // @PostMapping("/register")
    // public String register(@RequestBody UserModel user) {
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     userRepository.save(user);
    //     return "User registered successfully";
    // }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterUserDto registrationDetails) {
        AuthResponseDTO authResponseDTO = authService.registerUser(registrationDetails);
        ApiResponse<AuthResponseDTO> apiResponse = new ApiResponse<>(true, authResponseDTO,"User successully registered and logged in");

        return ResponseEntity.ok().body(apiResponse);
        
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody AuthRequestDto authRequestDto){
        AuthResponseDTO authResponseDTO = authService.loginUser(authRequestDto);
        ApiResponse<AuthResponseDTO> apiResponse = new ApiResponse<AuthResponseDTO>(false, authResponseDTO, "User successfully logged in");

        return ResponseEntity.ok().body(apiResponse);
    }

}
