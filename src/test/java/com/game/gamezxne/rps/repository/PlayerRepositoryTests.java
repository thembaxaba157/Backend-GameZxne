package com.game.gamezxne.rps.repository;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.game.gamezxne.rps.model.PlayerModel;
import com.game.gamezxne.rps.repository.PlayerRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlayerRepositoryTests {
    
    @Autowired
    private PlayerRepository playerRepository;


    @Test
    public void PlayerRepository_SaveAll_ReturnSavedPlayer(){
        // Arrange
        PlayerModel player = PlayerModel.builder().game(null).username("Player 1").build();
        
        playerRepository.save(player);

         //Assert
         Assertions.assertThat(player).isNotNull();
         Assertions.assertThat(player.getId()).isGreaterThan(0);


    }

}
