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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity

@Data
// @Getter
// @Setter
@Table(name = "game_model")

@EqualsAndHashCode
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lobbyName;

    @OneToMany(mappedBy = "game")
    @JsonManagedReference //research from https://chatgpt.com/c/be49b869-d3db-48a8-88ab-5430278e03f4
    private List<PlayerModel> currentPlayers;
        
    @Enumerated(EnumType.STRING)//From persistance, this will convert enum to string whenever requested or stored in the database
    // @Column(name = "game_status", nullable = false, columnDefinition = "VARCHAR")
    private GameStatus gameStatus;
    
    private int currentRound;
    
    private int numberOfRounds;

    

}
