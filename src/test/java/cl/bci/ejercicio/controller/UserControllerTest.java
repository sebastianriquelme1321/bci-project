package cl.bci.ejercicio.controller;

import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.dto.SignUpRequestDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private SignUpRequestDto signUpRequestDto;
    private SignUpResponseDto signUpResponseDto;
    private UserResponseDto userResponseDto;
    private PhoneDto phoneDto;

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_TOKEN = "test-jwt-token";

    @BeforeEach
    void setUp() {
        // Arrange - Configuración común
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        phoneDto = PhoneDto.builder()
                .number(123456789L)
                .citycode(1)
                .contrycode("57")
                .build();

        signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("Test User");
        signUpRequestDto.setEmail(TEST_EMAIL);
        signUpRequestDto.setPassword("Testpass12");
        signUpRequestDto.setPhones(Arrays.asList(phoneDto));

        signUpResponseDto = SignUpResponseDto.builder()
                .id(UUID.randomUUID())
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .token(TEST_TOKEN)
                .build();

        userResponseDto = UserResponseDto.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email(TEST_EMAIL)
                .password("Testpass12")
                .phones(Arrays.asList(phoneDto))
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token(TEST_TOKEN)
                .isActive(true)
                .build();
    }

    @Test
    void signUp_WhenValidRequest_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        when(userService.signUp(any(SignUpRequestDto.class))).thenReturn(signUpResponseDto);

        // Act & Assert
        mockMvc.perform(post("/v1/bci/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(signUpResponseDto.getId().toString()))
                .andExpect(jsonPath("$.token").value(TEST_TOKEN))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void signUp_WhenInvalidEmail_ShouldReturnBadRequest() throws Exception {
        // Arrange
        signUpRequestDto.setEmail("invalid-email");

        // Act & Assert
        mockMvc.perform(post("/v1/bci/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp_WhenEmptyName_ShouldReturnBadRequest() throws Exception {
        // Arrange
        signUpRequestDto.setName("");

        // Act & Assert
        mockMvc.perform(post("/v1/bci/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp_WhenInvalidPassword_ShouldReturnBadRequest() throws Exception {
        // Arrange
        signUpRequestDto.setPassword("invalid");

        // Act & Assert
        mockMvc.perform(post("/v1/bci/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_WhenValidToken_ShouldReturnOkStatus() throws Exception {
        // Arrange
        when(userService.login(anyString())).thenReturn(userResponseDto);

        // Act & Assert
        mockMvc.perform(post("/v1/bci/login")
                        .header("token", TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponseDto.getId().toString()))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value(TEST_EMAIL))
                .andExpect(jsonPath("$.token").value(TEST_TOKEN))
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void login_WhenMissingToken_ShouldReturnBadRequest() throws Exception {
        // Arrange - Sin token en el header

        // Act & Assert
        mockMvc.perform(post("/v1/bci/login"))
                .andExpect(status().isBadRequest());
    }
} 