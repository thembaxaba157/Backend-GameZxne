package com.game.rps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.game.rps.model.PlayerModel;
import com.game.rps.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    
    @Autowired
    private PlayerService playerService;

    @GetMapping
    public List<PlayerModel> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public PlayerModel getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id);
    }

    @PostMapping
    public PlayerModel createPlayer(@RequestBody PlayerModel player) {
        playerService.savePlayer(player);
        return player;
    }

    

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
    
    @PutMapping("/{id}")
    public PlayerModel updatePlayer(@PathVariable Long id, @RequestBody PlayerModel updatedPlayer) {
        PlayerModel existingPlayer = playerService.getPlayer(id);
        if (existingPlayer != null) {
            existingPlayer.setUsername(updatedPlayer.getUsername());
            existingPlayer.setScore(updatedPlayer.getScore());
            existingPlayer.setPlayerStatus(updatedPlayer.getPlayerStatus());
            existingPlayer.setMove(updatedPlayer.getMove());
            playerService.savePlayer(existingPlayer); // save the updated player
            return existingPlayer;
        } else {
            return null; // or handle error appropriately
        }
    }
}
