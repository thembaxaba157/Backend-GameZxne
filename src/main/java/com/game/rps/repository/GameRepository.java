package com.game.rps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.rps.model.GameModel;


public interface GameRepository extends JpaRepository<GameModel, Long > {

    List<GameModel> findGameModelByLobbyName(String lobbyName);
}

