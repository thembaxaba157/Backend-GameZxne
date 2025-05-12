package com.game.gamezxne.rps.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.rps.enums.Move;
import com.game.gamezxne.rps.enums.Status.PlayerGameStatus;
import com.game.gamezxne.rps.enums.Status.PlayerMoveStatus;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "player_model")
@EqualsAndHashCode
public class PlayerModel {


    public PlayerModel() {
    }
    
    public PlayerModel (UserModel user){
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id") //foreign key
    @JsonBackReference //research from https://chatgpt.com/c/be49b869-d3db-48a8-88ab-5430278e03f4
    private GameModel game;

    @Nonnull
    @OneToOne
    private UserModel user;
    private int score;
    
    @Enumerated(EnumType.STRING)
    private PlayerMoveStatus playerMoveStatus = PlayerMoveStatus.NOTPICKED;

    @Enumerated(EnumType.STRING)
    private PlayerGameStatus playerGameStatus;
    
    @Enumerated(EnumType.STRING)
    private Move pMove;


    public void statusReset(){
        this.playerMoveStatus = PlayerMoveStatus.NOTPICKED;
    }
   
}





