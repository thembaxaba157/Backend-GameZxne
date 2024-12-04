package com.game.gamezxne.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.gamezxne.auth.model.UserModel;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<UserModel, Long>{

    UserModel findByUsername(String username);

    
}