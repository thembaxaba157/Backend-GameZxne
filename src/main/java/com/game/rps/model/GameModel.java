package com.game.rps.model;

import java.util.List;

import com.game.rps.enums.GameStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lobbyName;

    @OneToMany
    private List<PlayerModel> currentPlayers;
        
    @Enumerated(EnumType.STRING) //From persistance, this will convert enum to string whenever requested or stored in the database
    private GameStatus gameStatus;
    
    private int currentRound;
    
    private int numberOfRounds;


}
