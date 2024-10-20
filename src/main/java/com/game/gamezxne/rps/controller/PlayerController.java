package com.game.gamezxne.rps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.gamezxne.rps.model.PlayerModel;
import com.game.gamezxne.rps.service.PlayerService;

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


    // @PostMapping("/create")
    // public PlayerModel createPlayer(@RequestBody PlayerModel player) {
    //     playerService.savePlayer(player);
    //     return player;
    // }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createPlayer() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = null;

        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        
       
        return new ResponseEntity<>(playerService.createPlayer(userName), HttpStatus.CREATED);
    }
    

    @GetMapping("/load")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> loadPlayer() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = null;

        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            userName = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        return new ResponseEntity<>(playerService.getPlayerByUsername(userName), HttpStatus.OK);

    }


    //saveforadmin
    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
    
    //saveforadmin
    @PutMapping("/{id}")
    public ResponseEntity<PlayerModel> updatePlayer(@PathVariable Long id, @RequestBody PlayerModel updatedPlayer) {
      return new ResponseEntity<>(playerService.updatePlayer(updatedPlayer, id), HttpStatus.OK);
           
    }
}
