package com.game.rps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.rps.model.PlayerModel;
import com.game.rps.repository.PlayerRepository;
import java.util.*;
@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;


    public List<PlayerModel> getAllPlayers(){
        return playerRepository.findAll();
    }

    public PlayerModel getPlayer(Long id){
        return playerRepository.findById(id).orElse(null); //fix to return an appropriate error
    }

    public void deletePlayer(Long id){
        playerRepository.deleteById(id); // fix for some appopriate error
    }

}
