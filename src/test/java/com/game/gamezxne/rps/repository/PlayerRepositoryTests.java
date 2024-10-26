package com.game.gamezxne.rps.repository;


import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.rps.model.PlayerModel;

import jakarta.transaction.Transactional;

@DataJpaTest
@ActiveProfiles("test")
// @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional

public class PlayerRepositoryTests {
    
    @Autowired
    private PlayerRepository playerRepository;

    @AfterEach
    public void clearRepo() {
        playerRepository.deleteAll();
    }
    
    @Test
    public void PlayerRepository_SaveAll_ReturnSavedPlayer(){
        

        // Arrange
        UserModel user =  new UserModel();
        user.setUsername("user1234");
        user.setPassword("strongpassword");
        user.setEmail("user1234@fakeemail.com");

        PlayerModel player = PlayerModel.builder().game(null).user(user).build();
        
        PlayerModel savedPlayer = playerRepository.save(player);

         //Assert
         Assertions.assertThat(savedPlayer).isNotNull();
         Assertions.assertThat(savedPlayer.getId()).isGreaterThan(0);


    }

    @Test
    public void reposSizeTest(){
        assertEquals(playerRepository.count(), 0);
    }

}