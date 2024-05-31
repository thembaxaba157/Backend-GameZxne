package com.game.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.rps.model.PlayerModel;

public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {
    
}
