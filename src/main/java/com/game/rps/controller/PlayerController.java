package com.game.rps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.rps.repository.PlayerRepository;
import com.game.rps.model.PlayerModel;
import java.util.*;
@RestController
@RequestMapping("/players")
public class PlayerController {
    
    @Autowired
    private PlayerRepository playerRepository;
    

    @GetMapping
    public List<PlayerModel> getAllPlayers() {
        return playerRepository.findAll();
}
}
