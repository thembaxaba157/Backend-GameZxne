package com.game.rps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.rps.enums.Status.GameStatus;
import com.game.rps.model.GameModel;
import com.game.rps.model.PlayerModel;
import com.game.rps.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    private final BroadcastService broadcastService;

    @Autowired
    public GameService(BroadcastService broadcastService){
        this.broadcastService = broadcastService;
    }
    

    public List<GameModel> getAllGames(){
        return gameRepository.findAll();
    }

    public GameModel getGame(Long id){
        return gameRepository.findById(id).orElse(null);
    }

    public void deleteGame(Long id){
        gameRepository.deleteById(id);
    }

    public void createGame(GameModel game){
        gameRepository.save(game);
    }

    
    @Transactional
    public void makeMove(PlayerModel playerModel, Long gameId){
        Optional<GameModel> game = gameRepository.findById(gameId);
        if(game.isPresent()){
            GameModel gameModel = game.get();
            if(gameModel.getGameStatus().equals(GameStatus.IN_PROGRESS)){
                
            }
        }

    }


}
