package com.game.rps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.rps.dto.GameResultDTO;
import com.game.rps.dto.MoveResponseDTO;
import com.game.rps.dto.PlayerMoveDTO;
import com.game.rps.enums.Move;
import com.game.rps.enums.MoveResponseMessage;
import com.game.rps.enums.Status.GameStatus;
import com.game.rps.enums.Status.PlayerStatus;
import com.game.rps.exceptions.GameNotFoundException;
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
        return gameRepository.findById(id).orElseThrow(()-> new GameNotFoundException("Game not Found"));
    }

    public void deleteGame(Long id){
        getGame(id); //it will get game if exists then will proceed to delete if not an exception will thrown
        gameRepository.deleteById(id);
    }

    public GameModel createGame(GameModel game){
       return gameRepository.save(game);
    }


    @Transactional
    public GameModel updateGame(GameModel gameUpdatesGameModel){
        GameModel currentGame = getGame(gameUpdatesGameModel.getId());
        currentGame.setCurrentPlayers(gameUpdatesGameModel.getCurrentPlayers());
        currentGame.setCurrentRound(gameUpdatesGameModel.getCurrentRound());
        currentGame.setGameStatus(gameUpdatesGameModel.getGameStatus());
        currentGame.setLobbyName(gameUpdatesGameModel.getLobbyName());
        currentGame.setNumberOfRounds(gameUpdatesGameModel.getNumberOfRounds());

        return gameRepository.save(currentGame);
        
    }



    @Transactional
    public GameModel addPlayer(Long playerid, Long gameid){
        GameModel gameModel = getGame(gameid);
        PlayerModel player = playerService.getPlayer(playerid);
        if(gameModel != null && player != null){
            List<PlayerModel> players = gameModel.getCurrentPlayers();
            if(!players.contains(player)){
            player.setGame(gameModel);
            players.add(player);
            gameModel.setCurrentPlayers(players);
            playerService.savePlayer(player);
            gameRepository.save(gameModel);
            }
        }
        return gameModel;
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
            gameModel = calculateScores(gameModel);
            broadcastService.broadcastMessage(toGameResultDTO(gameModel));

        }
        }

        return moveResponseDTO;

    }

    private GameResultDTO toGameResultDTO(GameModel gameModel){

        GameResultDTO gameResultDTO = new GameResultDTO();
        gameResultDTO.setGame(gameModel);
        if(gameModel.getGameStatus().equals(GameStatus.GAME_OVER)){
           gameResultDTO.setWinner(determineWinner(gameModel.getCurrentPlayers()));
        }

        return gameResultDTO;

    }

    private PlayerModel determineWinner(List<PlayerModel> currentPlayers) {
       PlayerModel winner = null;
       int maxScore = 0;
       for(PlayerModel player : currentPlayers){
        if(player.getScore() > maxScore){
            maxScore = player.getScore();
            winner = player;
            }
            }
        return winner;
    }


    @Transactional
    private GameModel calculateScores(GameModel gameModel) {
        List<PlayerModel> players = gameModel.getCurrentPlayers();
        for(int i=0; i<players.size();i++){
            PlayerModel player = players.get(i);
            for(int j=i+1; j<players.size();j++){
                PlayerModel opponent = players.get(j);
                decide(player,opponent);
        }
    }
    gameModel = setNextRound(gameModel);
    players = playerService.getAllPlayers();
    gameModel.setCurrentPlayers(players);
    return gameRepository.save(gameModel);
    }

   private GameModel setNextRound(GameModel gameModel) {
      if(gameModel.getCurrentRound()<gameModel.getNumberOfRounds()){
        gameModel.setCurrentRound(gameModel.getCurrentRound()+1);
        gameModel = playerStatusReset(gameModel);
      }
      else{
        gameModel.setGameStatus(GameStatus.GAME_OVER);
      }

      return gameModel;
    }


 private GameModel playerStatusReset(GameModel gameModel) {
	List<PlayerModel> players = gameModel.getCurrentPlayers();
    for(PlayerModel player : players){
        player.statusReset();
        playerService.savePlayer(player);
        }
    gameModel.setCurrentPlayers(playerService.getAllPlayers());
    return gameModel;
}


@Transactional
    private void decide(PlayerModel player, PlayerModel opponent) {
        if(player.getMove().equals(opponent.getMove())){
            player.setScore(player.getScore()+1);
            opponent.setScore(opponent.getScore()+1);

            playerService.savePlayer(player);
            playerService.savePlayer(opponent);
       }
       else if(player.getMove().equals(Move.PAPER) && opponent.getMove().equals(Move.ROCK)){
        player.setScore(player.getScore()+2);

        playerService.savePlayer(player);
       }
       else if(player.getMove().equals(Move.ROCK) && opponent.getMove().equals(Move.SCISSORS)){
        player.setScore(player.getScore()+2);

        playerService.savePlayer(player);
       }
       else if(player.getMove().equals(Move.SCISSORS) && opponent.getMove().equals(Move.PAPER)){
        player.setScore(player.getScore()+2);

        playerService.savePlayer(player);
       }



       else if(opponent.getMove().equals(Move.PAPER) && player.getMove().equals(Move.ROCK)){
        opponent.setScore(opponent.getScore()+2);

        playerService.savePlayer(opponent);
       }
       else if(opponent.getMove().equals(Move.ROCK) && player.getMove().equals(Move.SCISSORS)){
        opponent.setScore(opponent.getScore()+2);

        playerService.savePlayer(opponent);
       }
       else if(opponent.getMove().equals(Move.SCISSORS) && player.getMove().equals(Move.PAPER)){
        opponent.setScore(opponent.getScore()+2);

        playerService.savePlayer(opponent);
       }

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
