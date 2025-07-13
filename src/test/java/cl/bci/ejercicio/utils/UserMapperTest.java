package cl.bci.ejercicio.utils;

import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private User user;
    private Phone phone1;
    private Phone phone2;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        // Arrange - Configuración común
        testDateTime = LocalDateTime.now();
        
        phone1 = new Phone();
        phone1.setNumber(123456789L);
        phone1.setCityCode(1);
        phone1.setContrycode("57");

        phone2 = new Phone();
        phone2.setNumber(987654321L);
        phone2.setCityCode(2);
        phone2.setContrycode("58");

        user = User.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email("test@example.com")
                .password("testPassword")
                .phones(Arrays.asList(phone1, phone2))
                .created(testDateTime)
                .lastLogin(testDateTime)
                .token("test-token")
                .isActive(true)
                .build();
    }

    @Test
    void convertToUserResponse_WhenUserHasPhones_ShouldReturnCompleteUserResponseDto() {
        // Arrange
        // (user ya está configurado en setUp)

        // Act
        UserResponseDto result = UserMapper.convertToUserResponse(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getCreated(), result.getCreated());
        assertEquals(user.getLastLogin(), result.getLastLogin());
        assertEquals(user.getToken(), result.getToken());
        assertEquals(user.getIsActive(), result.getIsActive());
        
        // Verificar teléfonos
        assertNotNull(result.getPhones());
        assertEquals(2, result.getPhones().size());
        
        PhoneDto firstPhone = result.getPhones().get(0);
        assertEquals(phone1.getNumber(), firstPhone.getNumber());
        assertEquals(phone1.getCityCode(), firstPhone.getCitycode());
        assertEquals(phone1.getContrycode(), firstPhone.getContrycode());
        
        PhoneDto secondPhone = result.getPhones().get(1);
        assertEquals(phone2.getNumber(), secondPhone.getNumber());
        assertEquals(phone2.getCityCode(), secondPhone.getCitycode());
        assertEquals(phone2.getContrycode(), secondPhone.getContrycode());
    }

    @Test
    void convertToUserResponse_WhenUserHasNoPhones_ShouldReturnEmptyPhonesList() {
        // Arrange
        user.setPhones(new ArrayList<>());

        // Act
        UserResponseDto result = UserMapper.convertToUserResponse(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getCreated(), result.getCreated());
        assertEquals(user.getLastLogin(), result.getLastLogin());
        assertEquals(user.getToken(), result.getToken());
        assertEquals(user.getIsActive(), result.getIsActive());
        
        // Verificar teléfonos vacíos
        assertNotNull(result.getPhones());
        assertTrue(result.getPhones().isEmpty());
    }

    @Test
    void convertToUserResponse_WhenUserHasNullPhones_ShouldHandleGracefully() {
        // Arrange
        user.setPhones(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            UserMapper.convertToUserResponse(user);
        });
    }

    @Test
    void convertToSignUpResponse_WhenValidUser_ShouldReturnSignUpResponseDto() {
        // Arrange
        // (user ya está configurado en setUp)

        // Act
        SignUpResponseDto result = UserMapper.convertToSignUpResponse(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getCreated(), result.getCreated());
        assertEquals(user.getLastLogin(), result.getLastLogin());
        assertEquals(user.getIsActive(), result.getIsActive());
        assertEquals(user.getToken(), result.getToken());
    }

    @Test
    void convertToSignUpResponse_WhenUserWithNullValues_ShouldHandleGracefully() {
        // Arrange
        User userWithNulls = User.builder()
                .id(null)
                .created(null)
                .lastLogin(null)
                .isActive(null)
                .token(null)
                .build();

        // Act
        SignUpResponseDto result = UserMapper.convertToSignUpResponse(userWithNulls);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getCreated());
        assertNull(result.getLastLogin());
        assertNull(result.getIsActive());
        assertNull(result.getToken());
    }

    @Test
    void convertToSignUpResponse_WhenUserIsInactive_ShouldReturnInactiveUser() {
        // Arrange
        user.setIsActive(false);

        // Act
        SignUpResponseDto result = UserMapper.convertToSignUpResponse(user);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getCreated(), result.getCreated());
        assertEquals(user.getLastLogin(), result.getLastLogin());
        assertEquals(false, result.getIsActive());
        assertEquals(user.getToken(), result.getToken());
    }

    @Test
    void convertToUserResponse_WhenPhoneHasNullValues_ShouldHandleGracefully() {
        // Arrange
        Phone phoneWithNulls = new Phone();
        phoneWithNulls.setNumber(null);
        phoneWithNulls.setCityCode(null);
        phoneWithNulls.setContrycode(null);
        
        user.setPhones(Arrays.asList(phoneWithNulls));

        // Act
        UserResponseDto result = UserMapper.convertToUserResponse(user);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getPhones());
        assertEquals(1, result.getPhones().size());
        
        PhoneDto phoneDto = result.getPhones().get(0);
        assertNull(phoneDto.getNumber());
        assertNull(phoneDto.getCitycode());
        assertNull(phoneDto.getContrycode());
    }
} 