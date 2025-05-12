package com.game.gamezxne.rps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.auth.repository.UserRepository;
import com.game.gamezxne.rps.dto.PlayerDTO;
import com.game.gamezxne.rps.exceptions.PlayerNotFoundException;
import com.game.gamezxne.rps.model.PlayerModel;
import com.game.gamezxne.rps.repository.PlayerRepository;


@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private UserRepository userRepository;


    public List<PlayerModel> getAllPlayers(){
        return playerRepository.findAll();
    }

    public PlayerModel getPlayer(Long id){
        return playerRepository.findById(id).orElseThrow(()-> new PlayerNotFoundException("Player not Found")); //fix to return an appropriate error
    }

    public PlayerModel getPlayerModelByUsername(String username){
        UserModel user = userRepository.findByUsername(username);
        return playerRepository.findByUser(user);
    }

    public void deletePlayer(Long id){
        getPlayer(id); //check if player exists if not an exception will thrown
        playerRepository.deleteById(id); // fix for some appopriate error
    }

    public PlayerModel savePlayer(PlayerModel player){
        
       return playerRepository.save(player);
    }

    @Transactional
    public PlayerDTO createPlayer(String username){

        UserModel user = userRepository.findByUsername(username);
        PlayerModel playerModel = new PlayerModel(user);
        // PlayerModel playerModel = PlayerModel.builder().user(user).build();
        
        playerModel.setUser(user);

        return toPlayerDto(savePlayer(playerModel));
      
       
    }

    @Transactional
    public PlayerModel updatePlayer(PlayerModel updatedPlayer, Long id){
      
        PlayerModel existingPlayer = getPlayer(id);
        existingPlayer.setScore(updatedPlayer.getScore());
        existingPlayer.setPlayerMoveStatus(updatedPlayer.getPlayerMoveStatus());
        existingPlayer.setPMove(updatedPlayer.getPMove());
        return savePlayer(existingPlayer);
    }

    public PlayerDTO getPlayerByUsername(String userName) {
        UserModel user = findUserModelbyUsername(userName);

        return toPlayerDto(playerRepository.findByUser(user));
      
    }


    private PlayerDTO toPlayerDto(PlayerModel playerModel){
        PlayerDTO playerDTO = PlayerDTO.builder().id(playerModel.getId()).username(playerModel.getUser().getUsername()).build();
        return playerDTO;
    }

    private UserModel findUserModelbyUsername(String username){
        return userRepository.findByUsername(username);
    }

}
