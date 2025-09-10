package com.app.service;

import com.app.controller.dto.UserDTO;
import com.app.models.UserEntity;
import com.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUser() {
        UserDTO userDTO = UserDTO.builder()
                .username("testuser")
                .email("lauti@gmail.com")
                .password("password")
                .roles(Set.of("USER"))
                .build();

        when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        UserEntity savedUser = userService.addUser(userDTO);

        assert savedUser != null;
        assert savedUser.getUsername().equals("testuser");
    }
}
