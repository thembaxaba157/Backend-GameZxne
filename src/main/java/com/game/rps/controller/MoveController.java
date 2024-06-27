package com.game.rps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.rps.dto.MoveResponseDTO;
import com.game.rps.dto.PlayerMoveDTO;
import com.game.rps.service.GameService;

@RestController
@RequestMapping("/move")
public class MoveController {
    
    @Autowired
    private GameService gameService;

    @PostMapping
    public MoveResponseDTO makeMove(@RequestBody PlayerMoveDTO playerMoveDTO) {
        return gameService.makeMove(playerMoveDTO);
    }
}
