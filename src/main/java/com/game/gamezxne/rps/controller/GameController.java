package com.game.gamezxne.rps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.game.gamezxne.rps.dto.CreateGameDTO;
import com.game.gamezxne.rps.dto.MoveResponseDTO;
import com.game.gamezxne.rps.dto.PlayerMoveDTO;
import com.game.gamezxne.rps.model.GameModel;
import com.game.gamezxne.rps.service.GameService;


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
    public ResponseEntity<GameModel> getGame(@PathVariable Long id){
        return new ResponseEntity<>(gameService.getGame(id), HttpStatus.OK);
    }


    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GameModel> createGame(@RequestBody CreateGameDTO game){
        return new ResponseEntity<>(gameService.createGame(game), HttpStatus.CREATED);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return new ResponseEntity<>("Game Lobby deleted", HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GameModel> updateGame(@PathVariable Long id, @RequestBody GameModel updatedGame) {
      return new ResponseEntity<>(gameService.updateGame(updatedGame), HttpStatus.OK);
    }

    
    @PutMapping("join/{id}")
    public ResponseEntity<GameModel> joinGame(@PathVariable Long id, @RequestBody Long playerId){
        
       return new ResponseEntity<>(gameService.addPlayer(playerId, id),HttpStatus.OK);
    }

    @PostMapping("/move")
    @ResponseBody
    public ResponseEntity<MoveResponseDTO> makeMove(@RequestBody PlayerMoveDTO playerMoveDTO) {
        
        return new ResponseEntity<>(gameService.makeMove(playerMoveDTO), HttpStatus.OK);
    }

}
