package com.game.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.rps.model.GameModel;


public interface GameRepository extends JpaRepository<GameModel, Long > {
}

