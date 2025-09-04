package com.app.service;

import com.app.controller.dto.UserDTO;
import com.app.models.ERole;
import com.app.models.RoleEntity;
import com.app.models.UserEntity;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity addUser(UserDTO user) {

        System.out.println(user.toString());

        UserEntity newUser = UserEntity.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(mapRoles(user.getRoles()))
                .build();

        return userRepository.save(newUser);
    }

    private Set<RoleEntity> mapRoles(Set<String> strRoles) {
        return strRoles.stream()
                .map(role -> RoleEntity.builder().role(ERole.valueOf(role)).build())
                .collect(java.util.stream.Collectors.toSet());
    }

}
