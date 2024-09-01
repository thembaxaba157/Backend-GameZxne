package com.game.gamezxne.rps.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.game.gamezxne.rps.enums.Status.GameStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity

@Data
// @Getter
// @Setter

@EqualsAndHashCode
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lobbyName;

    @OneToMany
    @JsonManagedReference //research from https://chatgpt.com/c/be49b869-d3db-48a8-88ab-5430278e03f4
    private List<PlayerModel> currentPlayers;
        
    @Enumerated(EnumType.STRING) //From persistance, this will convert enum to string whenever requested or stored in the database
    private GameStatus gameStatus;
    
    private int currentRound;
    
    private int numberOfRounds;

    

}