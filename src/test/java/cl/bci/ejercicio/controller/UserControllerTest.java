package cl.bci.ejercicio.controller;

import cl.bci.ejercicio.dto.LoginRequest;
import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.dto.SignUpRequest;
import cl.bci.ejercicio.dto.UserResponse;
import cl.bci.ejercicio.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testSignUp_Success() throws Exception {
        // Arrange
        SignUpRequest request = SignUpRequest.builder()
                .name("Juan Pérez")
                .email("juan@example.com")
                .password("Password12")
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number(87650009L)
                        .citycode(7)
                        .contrycode("25")
                        .build()))
                .build();

        UserResponse response = UserResponse.builder()
                .id("uuid-123")
                .name("Juan Pérez")
                .email("juan@example.com")
                .token("jwt-token")
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(Collections.singletonList(PhoneDto.builder()
                        .number(87650009L)
                        .citycode(7)
                        .contrycode("25")
                        .build()))
                .build();

        when(userService.signUp(any(SignUpRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/sign-up")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("uuid-123"))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    @WithMockUser
    void testLogin_Success() throws Exception {
        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("juan@example.com")
                .password("Password12")
                .build();

        UserResponse response = UserResponse.builder()
                .id("uuid-123")
                .name("Juan Pérez")
                .email("juan@example.com")
                .token("jwt-token")
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(Collections.emptyList())
                .build();

        when(userService.login(any(LoginRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("uuid-123"))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    @WithMockUser
    void testSignUp_InvalidRequest() throws Exception {
        // Arrange
        SignUpRequest request = SignUpRequest.builder()
                .name("") // Nombre vacío
                .email("invalid-email") // Email inválido
                .password("invalid") // Contraseña inválida
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/sign-up")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void testLogin_InvalidRequest() throws Exception {
        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("invalid-email") // Email inválido
                .password("") // Contraseña vacía
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 