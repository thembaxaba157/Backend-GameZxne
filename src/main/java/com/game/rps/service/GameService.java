package com.game.rps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.rps.dto.MoveResponseDTO;
import com.game.rps.dto.PlayerMoveDTO;
import com.game.rps.enums.Move;
import com.game.rps.enums.MoveResponseMessage;
import com.game.rps.enums.Status.GameStatus;
import com.game.rps.enums.Status.PlayerStatus;
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
    public MoveResponseDTO makeMove(PlayerMoveDTO playerMoveDTO){
        Optional<GameModel> game = gameRepository.findById(playerMoveDTO.getGame_id());
        PlayerModel player = playerService.getPlayer(playerMoveDTO.getPlayer_id());
        MoveResponseDTO moveResponseDTO = new MoveResponseDTO();
        if(game.isPresent()){
            GameModel gameModel = game.get();
            if(player.getGame().equals(gameModel)){
            if(gameModel.getGameStatus().equals(GameStatus.IN_PROGRESS)){
                if(playerMoveDTO.getMove() instanceof Move && player.getPlayerStatus().equals(PlayerStatus.NOTPICKED)){
                    player.setPlayerStatus(PlayerStatus.PICKED);
                    player.setMove(playerMoveDTO.getMove());
                    moveResponseDTO.setGame_id(player.getId());
                    moveResponseDTO.setPlayerId(player.getId());
                    moveResponseDTO.setMoveResponseMessage(MoveResponseMessage.SUCCESS);
                 }
            }


        }

        if(allPlayersPicked(gameModel)){
            broadcastService.broadcastMessage("testing"); 
        }

        }

       


        return moveResponseDTO;

    }

    

        
        private boolean allPlayersPicked(GameModel gameModel) {
            if (gameModel.getCurrentPlayers() == null || gameModel.getCurrentPlayers().isEmpty()) {
                return false;
            }
    
            for (PlayerModel player : gameModel.getCurrentPlayers()) {
                if (player.getPlayerStatus() != PlayerStatus.PICKED) {
                    return false;
                }
            }
    
            return true;
        }

    

}
