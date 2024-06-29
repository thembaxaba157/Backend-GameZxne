package com.game.rps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.rps.model.GameModel;
import com.game.rps.service.GameService;


@RestController
@RequestMapping("/lobby")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public List<GameModel> getAllGames() {
        return gameService.getAllGames();
    }

    

    @GetMapping("/{id}")
    public GameModel getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }

    @PostMapping
    public GameModel createGame(@RequestBody GameModel game) {
        gameService.createGame(game);
        return game;
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
    
    @PutMapping("/{id}")
    public GameModel updateGame(@PathVariable Long id, @RequestBody GameModel updatedGame) {
        GameModel existingGame = gameService.getGame(id);
        if (existingGame != null) {
            existingGame.setLobbyName(updatedGame.getLobbyName());
            existingGame.setCurrentRound(updatedGame.getCurrentRound());
            existingGame.setNumberOfRounds(updatedGame.getNumberOfRounds());
            existingGame.setGameStatus(updatedGame.getGameStatus());
            gameService.createGame(existingGame); // save the updated game
            return existingGame;
        } else {
            return null; // or handle error appropriately
        }
    }

    @PutMapping("join/{id}")
    public GameModel joinGame(@PathVariable Long id, @RequestBody Long playerId){
        
       return gameService.addPlayer(playerId, id);
    }
}
