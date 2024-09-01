package com.game.gamezxne.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.gamezxne.rps.model.PlayerModel;

public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {
    
}