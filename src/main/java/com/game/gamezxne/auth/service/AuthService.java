package com.game.gamezxne.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.game.gamezxne.auth.dto.mapper.DtoMapper;
import com.game.gamezxne.auth.dto.request.AuthRequestDto;
import com.game.gamezxne.auth.dto.request.RegisterRequestDto;
import com.game.gamezxne.auth.dto.response.AuthResponseDTO;
import com.game.gamezxne.auth.dto.response.UserResponseDto;
import com.game.gamezxne.auth.jwt.JwtTokenProvider;
import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.auth.repository.UserRepository;
import com.game.gamezxne.exceptions.ResourceNotFound;

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



    public AuthResponseDTO registerUser(RegisterRequestDto registrationDetails) {
        System.out.println("did you reach 1");
        UserModel newUser = createUser(registrationDetails);
        String token = generateToken(newUser);
        
        
        return generateAuthResponse(newUser, token);
    }

    private AuthResponseDTO generateAuthResponse(UserModel user, String token){
        UserResponseDto userReponseDto = new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());    
        return new AuthResponseDTO(userReponseDto, token);
    }

    private UserModel createUser(RegisterRequestDto registrationDetailsu) {
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



    public List<UserResponseDto> getUsers() {
        return userRepository.findAll()
        .stream()
        .map(DtoMapper:: toUserResponseDto
        ).toList();
       
    }



    public UserResponseDto getUserbyId(Long id) {
        Optional<UserModel> user = userRepository.findById(id);

        return user.map(DtoMapper:: toUserResponseDto).orElseThrow(()-> new ResourceNotFound("User with ID"+ id + "Not Found"));
    }
    

}
