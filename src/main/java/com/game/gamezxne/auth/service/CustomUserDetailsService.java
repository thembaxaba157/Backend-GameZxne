package com.game.gamezxne.auth.service;


import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.auth.repository.UserRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        UserModel userModel = userRepository.findByUsername(username);
        if(userModel == null){
            throw new UsernameNotFoundException("User not found");
        }

        return new User(userModel.getUsername(), userModel.getPassword(), new ArrayList<>());
    }
    
}
