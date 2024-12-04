package com.game.gamezxne.rps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.rps.model.PlayerModel;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {
    
    PlayerModel findByUser(UserModel user);
}
