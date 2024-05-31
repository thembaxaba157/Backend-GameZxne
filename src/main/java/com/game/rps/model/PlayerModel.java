package com.game.rps.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import com.game.rps.enums.Move;

@Entity
@Getter
@Setter
public class PlayerModel {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id") //foreign key
    private GameModel game;
    private String username;
    private int score;

    @Enumerated(EnumType.STRING)
    private Move playerMove;

}





