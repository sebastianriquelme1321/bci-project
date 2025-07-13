package cl.bci.ejercicio.service;

import cl.bci.ejercicio.dto.LoginRequest;
import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.dto.SignUpRequest;
import cl.bci.ejercicio.dto.UserResponse;
import cl.bci.ejercicio.entity.User;
import cl.bci.ejercicio.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserService userService;

    @Test
    void testSignUp_Success() {
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

        User savedUser = User.builder()
                .id("uuid-123")
                .name("Juan Pérez")
                .email("juan@example.com")
                .password("encodedPassword")
                .token("jwt-token")
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .isActive(true)
                .phones(Collections.emptyList())
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(anyString())).thenReturn("jwt-token");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse response = userService.signUp(request);

        // Assert
        assertNotNull(response);
        assertEquals("uuid-123", response.getId());
        assertEquals("Juan Pérez", response.getName());
        assertEquals("juan@example.com", response.getEmail());
        assertEquals("jwt-token", response.getToken());
        assertTrue(response.getIsActive());

        verify(validationService).validateEmail("juan@example.com");
        verify(validationService).validatePassword("Password12");
        verify(userRepository).existsByEmail("juan@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSignUp_UserAlreadyExists() {
        // Arrange
        SignUpRequest request = SignUpRequest.builder()
                .name("Juan Pérez")
                .email("juan@example.com")
                .password("Password12")
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.signUp(request)
        );

        assertEquals("El correo ya registrado", exception.getMessage());
        verify(userRepository).existsByEmail("juan@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("juan@example.com")
                .password("Password12")
                .build();

        User existingUser = User.builder()
                .id("uuid-123")
                .name("Juan Pérez")
                .email("juan@example.com")
                .password("encodedPassword")
                .isActive(true)
                .phones(Collections.emptyList())
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("new-jwt-token");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserResponse response = userService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("uuid-123", response.getId());
        assertEquals("Juan Pérez", response.getName());
        assertEquals("juan@example.com", response.getEmail());
        assertTrue(response.getIsActive());

        verify(userRepository).findByEmail("juan@example.com");
        verify(passwordEncoder).matches("Password12", "encodedPassword");
        verify(jwtService).generateToken("juan@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("juan@example.com")
                .password("Password12")
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(request)
        );

        assertEquals("Usuario y/o contraseña inválidos", exception.getMessage());
        verify(userRepository).findByEmail("juan@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testLogin_InvalidPassword() {
        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("juan@example.com")
                .password("WrongPassword")
                .build();

        User existingUser = User.builder()
                .id("uuid-123")
                .email("juan@example.com")
                .password("encodedPassword")
                .isActive(true)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(request)
        );

        assertEquals("Usuario y/o contraseña inválidos", exception.getMessage());
        verify(userRepository).findByEmail("juan@example.com");
        verify(passwordEncoder).matches("WrongPassword", "encodedPassword");
    }

    @Test
    void testLogin_InactiveUser() {
        // Arrange
        LoginRequest request = LoginRequest.builder()
                .email("juan@example.com")
                .password("Password12")
                .build();

        User existingUser = User.builder()
                .id("uuid-123")
                .email("juan@example.com")
                .password("encodedPassword")
                .isActive(false)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.login(request)
        );

        assertEquals("Usuario inactivo", exception.getMessage());
        verify(userRepository).findByEmail("juan@example.com");
        verify(passwordEncoder).matches("Password12", "encodedPassword");
    }
} 