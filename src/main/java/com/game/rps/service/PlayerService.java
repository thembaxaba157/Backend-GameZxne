package com.game.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.rps.repository.PlayerRepository;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;
}
