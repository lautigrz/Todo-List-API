package com.app.controller.dto;

import com.app.models.RoleEntity;
import com.app.models.TaskEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    private String password;

    private Set<String> roles;

}
