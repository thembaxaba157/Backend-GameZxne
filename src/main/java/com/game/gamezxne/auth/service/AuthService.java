package com.game.gamezxne.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.game.gamezxne.auth.dto.AuthRequestDto;
import com.game.gamezxne.auth.dto.AuthResponseDTO;
import com.game.gamezxne.auth.dto.RegisterUserDto;
import com.game.gamezxne.auth.dto.UserReponseDto;
import com.game.gamezxne.auth.jwt.JwtTokenProvider;
import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;


@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService){
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }



    public AuthResponseDTO registerUser(RegisterUserDto registrationDetails) {
        UserModel newUser = createUser(registrationDetails);
        String token = generateToken(newUser);
        
        
        return generateAuthResponse(newUser, token);
    }

    private AuthResponseDTO generateAuthResponse(UserModel user, String token){
        UserReponseDto userReponseDto = new UserReponseDto();
        userReponseDto.setEmail(user.getEmail());
        userReponseDto.setUsername(user.getUsername());
        userReponseDto.setId(user.getId());
        

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setUserReponseDto(userReponseDto);
        authResponseDTO.setUserSessionToken(token);
        return authResponseDTO;
    }

    private UserModel createUser(RegisterUserDto registrationDetailsu) {
        UserModel userModel = new UserModel();

        //TODO VALIDATE AND ERROR HANDLE
        userModel.setEmail(registrationDetailsu.getEmail());
        userModel.setUsername(registrationDetailsu.getUsername());
        userModel.setPassword(passwordEncoder.encode(registrationDetailsu.getPassword()));
        return userRepository.save(userModel);
    }

    private String generateToken(UserModel newUser) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
        return jwtTokenProvider.generateToken(userDetails);
        
    }
    private String generateToken(AuthRequestDto authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return jwtTokenProvider.generateToken(userDetails);
    }

    public AuthResponseDTO loginUser(AuthRequestDto authRequestDto) {
        String token = generateToken(authRequestDto);
        UserModel user = userRepository.findByUsername(authRequestDto.getUsername());
        

        return generateAuthResponse(user, token);
    }
    

}
