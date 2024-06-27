package com.game.rps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.game.rps.model.GameModel;
import com.game.rps.service.GameService;

import java.util.List;


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
}
