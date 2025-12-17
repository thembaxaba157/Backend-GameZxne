package com.game.gamezxne;

import static org.assertj.core.api.Assertions.assertThat;

import com.game.gamezxne.auth.model.UserModel;
import com.game.gamezxne.auth.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test") // ⬅ Uses H2
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_shouldReturnUser() {
        // given
        UserModel user = new UserModel();
        user.setUsername("themba");
        user.setEmail("themba@test.com");
        user.setPassword("secret");

        userRepository.save(user);

        // when
        UserModel found = userRepository.findByUsername("themba");

        // then
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("themba");
    }

    // @Test
    // void existsByUsername_shouldReturnTrue() {
    //     UserModel user = new UserModel();
    //     user.setUsername("admin");
    //     user.setEmail("admin@test.com");
    //     user.setPassword("secret");

    //     userRepository.save(user);

    //     boolean exists = userRepository.existsByUsername("admin");

    //     assertThat(exists).isTrue();
    // }

    // @Test
    // void existsByEmail_shouldReturnFalse_whenNotExists() {
    //     boolean exists = userRepository.existsByEmail("missing@test.com");

    //     assertThat(exists).isFalse();
    // }
}
