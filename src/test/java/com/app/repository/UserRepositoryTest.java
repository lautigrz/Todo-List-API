package com.app.repository;

import com.app.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        UserEntity user = UserEntity.builder()
                .username("testuser")
                .password("password")
                .email("lauti@gmail.com")
                .build();
        userRepository.save(user);

        UserEntity foundUser = userRepository.findByUsername("testuser").orElse(null);

        assertThat(foundUser).isNotNull();
        assert foundUser != null;
        assertThat(foundUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testFindByUsernameNotFound() {
        UserEntity foundUser = userRepository.findByUsername("nonexistent").orElse(null);
        assertThat(foundUser).isNull();
    }

    @Test
    public void testCaptureException() {
      UserEntity user = UserEntity.builder()
                .username("testuser")
                .password("password")
                .email("lauti@gmail.com")
                .build();
        userRepository.save(user);

        assertThrows(UsernameNotFoundException.class, () -> {
            userRepository.findByUsername("user")
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        });
    }

}
