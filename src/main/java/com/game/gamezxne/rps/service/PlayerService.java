package com.game.gamezxne.rps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.gamezxne.rps.exceptions.PlayerNotFoundException;
import com.game.gamezxne.rps.model.PlayerModel;
import com.game.gamezxne.rps.repository.PlayerRepository;

import jakarta.transaction.Transactional;
@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;


    public List<PlayerModel> getAllPlayers(){
        return playerRepository.findAll();
    }

    public PlayerModel getPlayer(Long id){
        return playerRepository.findById(id).orElseThrow(()-> new PlayerNotFoundException("Player not Found")); //fix to return an appropriate error
    }

    public void deletePlayer(Long id){
        getPlayer(id); //check if player exists if not an exception will thrown
        playerRepository.deleteById(id); // fix for some appopriate error
    }

    public PlayerModel savePlayer(PlayerModel player){
        
       return playerRepository.save(player);
    }

    @Transactional
    public PlayerModel updatePlayer(PlayerModel updatedPlayer, Long id){
      
        PlayerModel existingPlayer = getPlayer(id);
      
        existingPlayer.setUsername(updatedPlayer.getUsername());
        existingPlayer.setScore(updatedPlayer.getScore());
        existingPlayer.setPlayerStatus(updatedPlayer.getPlayerStatus());
        existingPlayer.setMove(updatedPlayer.getMove());
        return savePlayer(existingPlayer);
    }
    
}
