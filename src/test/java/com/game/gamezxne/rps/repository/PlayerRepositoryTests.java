package com.game.gamezxne.rps.repository;


import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.game.gamezxne.rps.model.PlayerModel;

import jakarta.transaction.Transactional;

// @DataJpaTest
@SpringBootTest(properties = { "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect" })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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
        PlayerModel player = PlayerModel.builder().game(null).username("Player 1").build();
        
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