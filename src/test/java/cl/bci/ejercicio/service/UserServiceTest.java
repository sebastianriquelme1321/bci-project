package cl.bci.ejercicio.service;

import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.dto.SignUpRequestDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;
import cl.bci.ejercicio.exception.UserAlReadyExist;
import cl.bci.ejercicio.exception.UserNotFoundException;
import cl.bci.ejercicio.repository.UserRepository;
import cl.bci.ejercicio.utils.AESUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private SignUpRequestDto signUpRequestDto;
    private User mockUser;
    private PhoneDto phoneDto;
    private Phone phone;
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_TOKEN = "test-jwt-token";

    @BeforeEach
    void setUp() throws Exception {
        // Arrange - Configuración común para todos los tests
        phoneDto = PhoneDto.builder()
                .number(123456789L)
                .citycode(1)
                .contrycode("57")
                .build();

        signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("Test User");
        signUpRequestDto.setEmail(TEST_EMAIL);
        signUpRequestDto.setPassword("TestPass1234");
        signUpRequestDto.setPhones(Arrays.asList(phoneDto));

        phone = new Phone();
        phone.setNumber(123456789L);
        phone.setCityCode(1);
        phone.setContrycode("57");

        mockUser = User.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email(TEST_EMAIL)
                .password(AESUtil.encrypt("TestPass1234"))
                .phones(Arrays.asList(phone))
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token(TEST_TOKEN)
                .isActive(true)
                .build();

        phone.setUser(mockUser);
    }

    @Test
    void signUp_WhenUserDoesNotExist_ShouldCreateUserSuccessfully() throws Exception {
        // Arrange
        when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(false);
        when(jwtService.generateToken(TEST_EMAIL)).thenReturn(TEST_TOKEN);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        SignUpResponseDto result = userService.signUp(signUpRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getCreated(), result.getCreated());
        assertEquals(mockUser.getLastLogin(), result.getLastLogin());
        assertEquals(mockUser.getIsActive(), result.getIsActive());
        assertEquals(TEST_TOKEN, result.getToken());

        verify(userRepository).existsByEmail(TEST_EMAIL);
        verify(jwtService).generateToken(TEST_EMAIL);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signUp_WhenUserAlreadyExists_ShouldThrowUserAlReadyExist() {
        // Arrange
        when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(true);

        // Act & Assert
        UserAlReadyExist exception = assertThrows(UserAlReadyExist.class, () -> {
            userService.signUp(signUpRequestDto);
        });

        assertEquals("Ya existe un usuario activo registrado con el mismo email", exception.getMessage());
        verify(userRepository).existsByEmail(TEST_EMAIL);
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void signUp_WhenPhonesAreNull_ShouldCreateUserWithoutPhones() throws Exception {
        // Arrange
        signUpRequestDto.setPhones(null);
        User userWithoutPhones = User.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email(TEST_EMAIL)
                .password("TestPass1234")
                .phones(null)
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token(TEST_TOKEN)
                .isActive(true)
                .build();

        when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(false);
        when(jwtService.generateToken(TEST_EMAIL)).thenReturn(TEST_TOKEN);
        when(userRepository.save(any(User.class))).thenReturn(userWithoutPhones);

        // Act
        SignUpResponseDto result = userService.signUp(signUpRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(userWithoutPhones.getId(), result.getId());
        assertEquals(TEST_TOKEN, result.getToken());

        verify(userRepository).existsByEmail(TEST_EMAIL);
        verify(jwtService).generateToken(TEST_EMAIL);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_WhenUserExists_ShouldReturnUserResponseDto() throws Exception {
        // Arrange
        when(jwtService.extractEmail(TEST_TOKEN)).thenReturn(TEST_EMAIL);
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        UserResponseDto result = userService.login(TEST_TOKEN);

        // Assert
        assertNotNull(result);
        assertEquals(mockUser.getId(), result.getId());
        assertEquals(mockUser.getName(), result.getName());
        assertEquals(mockUser.getEmail(), result.getEmail());
        assertEquals("TestPass1234", result.getPassword());
        assertEquals(mockUser.getIsActive(), result.getIsActive());
        assertEquals(TEST_TOKEN, result.getToken());
        assertNotNull(result.getPhones());
        assertEquals(1, result.getPhones().size());

        verify(jwtService).extractEmail(TEST_TOKEN);
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
        // Arrange
        when(jwtService.extractEmail(TEST_TOKEN)).thenReturn(TEST_EMAIL);
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.login(TEST_TOKEN);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(jwtService).extractEmail(TEST_TOKEN);
        verify(userRepository).findByEmail(TEST_EMAIL);
        verify(userRepository, never()).save(any(User.class));
    }
} 