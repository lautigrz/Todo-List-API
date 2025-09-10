package com.app.controller;

import com.app.config.jwt.JwtUtils;
import com.app.controller.dto.TaskDTO;
import com.app.controller.dto.UserDTO;
import com.app.models.UserEntity;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserDTO userDTO = UserDTO.builder()
                .username("testuser")
                .email("test@test.com")
                .password("123456")
                .roles(Set.of("USER"))
                .build();
        userService.addUser(userDTO);
    }
    @Test
    public void deberiaAñadirUnaTareaUnUsuarioAutenticado() throws Exception {

        String token = jwtUtils.generateToken("testuser");

        ObjectMapper mapper = new ObjectMapper();
        String taskJson = mapper.writeValueAsString(new TaskDTO("Test Task", "This is a test task"));

        mockMvc.perform(post("/addTask")
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .content(taskJson)
                )
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("description").value("This is a test task"))
                .andExpect(status().isOk());

    }


    @Test
    public void cuandoAccedarSinTokenDeberiaDevolverUnauthorized() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String taskJson = mapper.writeValueAsString(new TaskDTO("Test Task", "This is a test task"));

        mockMvc.perform(post("/addTask")
                .contentType("application/json")
                .content(taskJson)
        )
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void cuandoAccedaAUnEndpointConRolDiferente_RetornaForbbiden() throws Exception {

        String token = jwtUtils.generateToken("testuser");

        mockMvc.perform(get("/admin/tasks")
        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testEndpointConWithMockUser() throws Exception {
        mockMvc.perform(get("/admin/tasks")) // no envía JWT
                .andExpect(status().isOk());
    }

}


