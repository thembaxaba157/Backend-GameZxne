package com.game.gamezxne.rps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.gamezxne.rps.dto.CreateGameDTO;
import com.game.gamezxne.rps.dto.EndGameResultDTO;
import com.game.gamezxne.rps.dto.GameStateDTO;
import com.game.gamezxne.rps.dto.MoveResponseDTO;
import com.game.gamezxne.rps.dto.PlayerMoveDTO;
import com.game.gamezxne.rps.dto.PlayerResultDTO;
import com.game.gamezxne.rps.dto.RoundResultDTO;
import com.game.gamezxne.rps.enums.Move;
import com.game.gamezxne.rps.enums.MoveResponseMessage;
import com.game.gamezxne.rps.enums.Status.GameStatus;
import com.game.gamezxne.rps.enums.Status.PlayerGameStatus;
import com.game.gamezxne.rps.enums.Status.PlayerMoveStatus;
import com.game.gamezxne.rps.exceptions.GameNotFoundException;
import com.game.gamezxne.rps.model.GameModel;
import com.game.gamezxne.rps.model.PlayerModel;
import com.game.gamezxne.rps.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    private final BroadcastService broadcastService;

    @Autowired
    public GameService(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    public List<GameModel> getAllGames() {
        return gameRepository.findAll();
    }

    public GameModel getGame(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game not Found"));
    }

    public void deleteGame(Long id) {
        getGame(id); // it will get game if exists then will proceed to delete if not an exception
                     // will thrown
        gameRepository.deleteById(id);
    }

    public GameModel createGame(CreateGameDTO game, String userName) {

        PlayerModel player = playerService.getPlayerModelByUsername(userName);
        if (player.getId() == game.getPlayerId()) {
            // complete for verification
        }
        GameModel gameModel = convertToGameModel(game, player);
        gameModel.setGameStatus(GameStatus.WAITING_FOR_PLAYERS);
        return gameRepository.save(gameModel);
    }

    @Transactional
    private GameModel convertToGameModel(CreateGameDTO gameDTO, PlayerModel player) {

        GameModel game = new GameModel();
        game.setLobbyName(gameDTO.getLobbyName());
        game.setNumberOfRounds(gameDTO.getNumberOfRounds());
        game.setLobbyMasterPlayer(player);
        game.setNumberOfRounds(gameDTO.getNumberOfPlayers());
        gameRepository.save(game);
        game = addPlayer(player.getId(), game.getId());

        return game;
    }

    @Transactional
    public GameModel updateGame(GameModel gameUpdatesGameModel) {
        GameModel currentGame = getGame(gameUpdatesGameModel.getId());
        currentGame.setCurrentPlayers(gameUpdatesGameModel.getCurrentPlayers());
        currentGame.setCurrentRound(gameUpdatesGameModel.getCurrentRound());
        currentGame.setGameStatus(gameUpdatesGameModel.getGameStatus());
        currentGame.setLobbyName(gameUpdatesGameModel.getLobbyName());
        currentGame.setNumberOfRounds(gameUpdatesGameModel.getNumberOfRounds());

        return gameRepository.save(currentGame);

    }

    @Transactional
    public GameModel addPlayer(Long playerid, Long gameid) {
        GameModel gameModel = getGame(gameid);
        PlayerModel player = playerService.getPlayer(playerid);
        if (gameModel != null && player != null) {
            List<PlayerModel> players = gameModel.getCurrentPlayers();
            if (!players.contains(player)) {
                player.setGame(gameModel);
                player.setPlayerGameStatus(setPlayerStatusOnGame(gameModel.getGameStatus()));
                players.add(player);
                gameModel.setCurrentPlayers(players);
                playerService.savePlayer(player);
                if (gameModel.getNumberOfPlayers() == gameModel.getCurrentPlayers().size()) {
                    gameModel.setGameStatus(GameStatus.IN_PROGRESS);
                    changePlayerStatus(gameModel);

                }
                gameRepository.save(gameModel);
                for (PlayerModel currPlayer : players) {
                    String message = player.getUser().getUsername() + " has joined the lobby";
                    if (currPlayer.getUser().getUsername() != player.getUser().getUsername()) {
                        broadcastService.broadcastMessage(message, currPlayer.getUser().getUsername());
                    }
                }

            }
        }

        return gameModel;
    }

    private void changePlayerStatus(GameModel gameModel) {
        for(PlayerModel player: gameModel.getCurrentPlayers()){
            player.setPlayerGameStatus(setPlayerStatusOnGame(gameModel.getGameStatus()));
            playerService.savePlayer(player);
        }
    }

    private PlayerGameStatus setPlayerStatusOnGame(GameStatus gameStatus) {
        if (gameStatus == GameStatus.WAITING_FOR_PLAYERS) {
            return PlayerGameStatus.WAITING_FOR_GAME_START;
        }

        return PlayerGameStatus.IN_PROGRESS;
    }

    @Transactional
    public MoveResponseDTO makeMove(PlayerMoveDTO playerMoveDTO) {
        Optional<GameModel> game = gameRepository.findById(playerMoveDTO.getGame_id());
        PlayerModel player = playerService.getPlayer(playerMoveDTO.getPlayer_id());
        MoveResponseDTO moveResponseDTO = new MoveResponseDTO();
        if (game.isPresent()) {
            GameModel gameModel = game.get();
            if (player.getGame().equals(gameModel)) {
                if (gameModel.getGameStatus().equals(GameStatus.IN_PROGRESS)) {
                    if (playerMoveDTO.getMove() instanceof Move
                            && player.getPlayerMoveStatus().equals(PlayerMoveStatus.NOTPICKED)) {
                        player.setPlayerMoveStatus(PlayerMoveStatus.PICKED);
                        player.setPMove(playerMoveDTO.getMove());
                        moveResponseDTO.setGame_id(player.getId());
                        moveResponseDTO.setPlayerId(player.getId());
                        moveResponseDTO.setMoveResponseMessage(MoveResponseMessage.SUCCESS);
                    }
                }
            }

            if (allPlayersPicked(gameModel)) {
                gameModel = calculateScores(gameModel);
                sendIndividualScores(gameModel);
                if (gameModel.getGameStatus() == GameStatus.IN_PROGRESS) {
                    broadcastService.broadcastMessage(toGameStateDTO(gameModel), gameModel.getLobbyName());
                } else if (gameModel.getGameStatus() == GameStatus.GAME_OVER
                        || gameModel.getGameStatus() == GameStatus.DEATH_ROUNDS) {
                    broadcastService.broadcastMessage(toEndGameResultDTO(gameModel), gameModel.getLobbyName());
                }

            } else {
                broadcastService.broadcastMessage(player.getUser().getUsername() + " has picked",
                        gameModel.getLobbyName());
            }
        }

        return moveResponseDTO;

    }

    private EndGameResultDTO toEndGameResultDTO(GameModel gameModel) {
        EndGameResultDTO endGameResultDTO = new EndGameResultDTO();
        RoundResultDTO roundResultDTO = toRoundResultDTO(gameModel);
        endGameResultDTO.setRoundResult(roundResultDTO);
        endGameResultDTO.setGameStatus(gameModel.getGameStatus());
        for (PlayerModel player : gameModel.getCurrentPlayers()) {
            endGameResultDTO.addPlayerFinalOutcome(player.getUser().getUsername(), player.getPlayerGameStatus());
        }

        return endGameResultDTO;
    }

    private GameStateDTO toGameStateDTO(GameModel gameModel) {
        GameStateDTO gameStateDTO = new GameStateDTO();
        RoundResultDTO roundResultDTO = toRoundResultDTO(gameModel);
        gameStateDTO.setRoundResult(roundResultDTO);
        gameStateDTO.setCurrRound(gameModel.getCurrentRound());
        gameStateDTO.setGameStatus(gameModel.getGameStatus());
        gameStateDTO.setMaxNumberRounds(gameModel.getNumberOfRounds());
        return gameStateDTO;

    }

    private void sendIndividualScores(GameModel gameModel) {
        for (PlayerModel player : gameModel.getCurrentPlayers()) {
            PlayerResultDTO playerResultDTO = convertToPlayerResultDTO(player);
            broadcastService.broadcastMessage(playerResultDTO, player.getUser().getUsername());
        }

    }

    private PlayerResultDTO convertToPlayerResultDTO(PlayerModel player) {
        PlayerResultDTO playerResultDTO = new PlayerResultDTO();
        playerResultDTO.setUsername(player.getUser().getUsername());
        playerResultDTO.setScore(player.getScore());
        playerResultDTO.setRound(player.getGame().getCurrentRound());
        playerResultDTO.setResult(player.getPlayerGameStatus());
        return playerResultDTO;
    }

    private RoundResultDTO toRoundResultDTO(GameModel gameModel) {

        RoundResultDTO roundResultDTO = new RoundResultDTO();
        roundResultDTO.setRound(gameModel.getCurrentRound() - 1);
        for (PlayerModel player : gameModel.getCurrentPlayers()) {
            roundResultDTO.addPlayerScore(player.getUser().getUsername(), player.getScore());
        }

        return roundResultDTO;

    }

    @Transactional
    private GameModel calculateScores(GameModel gameModel) {
        List<PlayerModel> players = gameModel.getCurrentPlayers();
        for (int i = 0; i < players.size(); i++) {
            PlayerModel player = players.get(i);
            for (int j = i + 1; j < players.size(); j++) {
                PlayerModel opponent = players.get(j);
                decide(player, opponent);
            }
        }
        gameModel = setNextRound(gameModel);
        players = playerService.getAllPlayers();
        gameModel.setCurrentPlayers(players);
        return gameRepository.save(gameModel);
    }

    private GameModel setNextRound(GameModel gameModel) {
        PlayerGameStatus maxScoreResultAssignment = PlayerGameStatus.DRAW;
        if (gameModel.getCurrentRound() < gameModel.getNumberOfRounds()) {
            gameModel.setCurrentRound(gameModel.getCurrentRound() + 1);
            gameModel = playerStatusReset(gameModel);
        } else {

            gameModel.setGameStatus(GameStatus.GAME_OVER);
            int maxScore = gameModel.getCurrentPlayers().stream()
                    .mapToInt(PlayerModel::getScore)
                    .max()
                    .orElse(0);

            int playersWithMaxScore = (int) gameModel.getCurrentPlayers().stream()
                    .filter(player -> player.getScore() == maxScore)
                    .count();
            if (playersWithMaxScore > 1) {
                maxScoreResultAssignment = PlayerGameStatus.DRAW;
            }
            for (PlayerModel currPlayer : gameModel.getCurrentPlayers()) {
                if (currPlayer.getScore() == maxScore) {
                    currPlayer.setPlayerGameStatus(maxScoreResultAssignment);
                } else {
                    currPlayer.setPlayerGameStatus(PlayerGameStatus.LOSE);
                }

            }
        }

        return gameModel;
    }

    private GameModel playerStatusReset(GameModel gameModel) {
        List<PlayerModel> players = gameModel.getCurrentPlayers();
        for (PlayerModel player : players) {
            player.statusReset();
            playerService.savePlayer(player);
        }
        gameModel.setCurrentPlayers(playerService.getAllPlayers());
        return gameModel;
    }

    @Transactional
    private void decide(PlayerModel player, PlayerModel opponent) {
        if (player.getPMove().equals(opponent.getPMove())) {
            player.setScore(player.getScore() + 1);
            opponent.setScore(opponent.getScore() + 1);

            playerService.savePlayer(player);
            playerService.savePlayer(opponent);
        } else if (player.getPMove().equals(Move.PAPER) && opponent.getPMove().equals(Move.ROCK)) {
            player.setScore(player.getScore() + 2);

            playerService.savePlayer(player);
        } else if (player.getPMove().equals(Move.ROCK) && opponent.getPMove().equals(Move.SCISSORS)) {
            player.setScore(player.getScore() + 2);

            playerService.savePlayer(player);
        } else if (player.getPMove().equals(Move.SCISSORS) && opponent.getPMove().equals(Move.PAPER)) {
            player.setScore(player.getScore() + 2);

            playerService.savePlayer(player);
        }

        else if (opponent.getPMove().equals(Move.PAPER) && player.getPMove().equals(Move.ROCK)) {
            opponent.setScore(opponent.getScore() + 2);

            playerService.savePlayer(opponent);
        } else if (opponent.getPMove().equals(Move.ROCK) && player.getPMove().equals(Move.SCISSORS)) {
            opponent.setScore(opponent.getScore() + 2);

            playerService.savePlayer(opponent);
        } else if (opponent.getPMove().equals(Move.SCISSORS) && player.getPMove().equals(Move.PAPER)) {
            opponent.setScore(opponent.getScore() + 2);

            playerService.savePlayer(opponent);
        }

    }

    private boolean allPlayersPicked(GameModel gameModel) {
        if (gameModel.getCurrentPlayers() == null || gameModel.getCurrentPlayers().isEmpty()) {
            return false;
        }

        for (PlayerModel player : gameModel.getCurrentPlayers()) {
            if (player.getPlayerMoveStatus() != PlayerMoveStatus.PICKED) {
                return false;
            }
        }

        return true;
    }

}
