package cl.bci.ejercicio.utils;

import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PhoneMapperTest {

    private User user;
    private PhoneDto phoneDto1;
    private PhoneDto phoneDto2;

    @BeforeEach
    void setUp() {
        // Arrange - Configuración común
        user = User.builder()
                .id(UUID.randomUUID())
                .name("Test User")
                .email("test@example.com")
                .password("testPassword")
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("test-token")
                .isActive(true)
                .build();

        phoneDto1 = PhoneDto.builder()
                .number(123456789L)
                .citycode(1)
                .contrycode("57")
                .build();

        phoneDto2 = PhoneDto.builder()
                .number(987654321L)
                .citycode(2)
                .contrycode("58")
                .build();
    }

    @Test
    void toEntity_WhenValidPhoneDto_ShouldReturnPhoneEntity() {
        // Arrange
        // (phoneDto1 y user ya están configurados en setUp)

        // Act
        Phone result = PhoneMapper.toEntity(phoneDto1, user);

        // Assert
        assertNotNull(result);
        assertEquals(phoneDto1.getNumber(), result.getNumber());
        assertEquals(phoneDto1.getCitycode(), result.getCityCode());
        assertEquals(phoneDto1.getContrycode(), result.getContrycode());
        assertEquals(user, result.getUser());
    }

    @Test
    void toEntity_WhenPhoneDtoWithNullValues_ShouldHandleGracefully() {
        // Arrange
        PhoneDto phoneDtoWithNulls = PhoneDto.builder()
                .number(null)
                .citycode(null)
                .contrycode(null)
                .build();

        // Act
        Phone result = PhoneMapper.toEntity(phoneDtoWithNulls, user);

        // Assert
        assertNotNull(result);
        assertNull(result.getNumber());
        assertNull(result.getCityCode());
        assertNull(result.getContrycode());
        assertEquals(user, result.getUser());
    }

    @Test
    void toEntity_WhenNullUser_ShouldHandleGracefully() {
        // Arrange
        User nullUser = null;

        // Act
        Phone result = PhoneMapper.toEntity(phoneDto1, nullUser);

        // Assert
        assertNotNull(result);
        assertEquals(phoneDto1.getNumber(), result.getNumber());
        assertEquals(phoneDto1.getCitycode(), result.getCityCode());
        assertEquals(phoneDto1.getContrycode(), result.getContrycode());
        assertNull(result.getUser());
    }

    @Test
    void toEntity_WhenNullPhoneDto_ShouldThrowNullPointerException() {
        // Arrange
        PhoneDto nullPhoneDto = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            PhoneMapper.toEntity(nullPhoneDto, user);
        });
    }

    @Test
    void toEntityList_WhenValidPhoneDtoList_ShouldReturnPhoneEntityList() {
        // Arrange
        List<PhoneDto> phoneDtos = Arrays.asList(phoneDto1, phoneDto2);

        // Act
        List<Phone> result = PhoneMapper.toEntityList(phoneDtos, user);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verificar primer teléfono
        Phone firstPhone = result.get(0);
        assertEquals(phoneDto1.getNumber(), firstPhone.getNumber());
        assertEquals(phoneDto1.getCitycode(), firstPhone.getCityCode());
        assertEquals(phoneDto1.getContrycode(), firstPhone.getContrycode());
        assertEquals(user, firstPhone.getUser());
        
        // Verificar segundo teléfono
        Phone secondPhone = result.get(1);
        assertEquals(phoneDto2.getNumber(), secondPhone.getNumber());
        assertEquals(phoneDto2.getCitycode(), secondPhone.getCityCode());
        assertEquals(phoneDto2.getContrycode(), secondPhone.getContrycode());
        assertEquals(user, secondPhone.getUser());
    }

    @Test
    void toEntityList_WhenEmptyPhoneDtoList_ShouldReturnEmptyList() {
        // Arrange
        List<PhoneDto> emptyList = Arrays.asList();

        // Act
        List<Phone> result = PhoneMapper.toEntityList(emptyList, user);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntityList_WhenNullPhoneDtoList_ShouldThrowNullPointerException() {
        // Arrange
        List<PhoneDto> nullList = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            PhoneMapper.toEntityList(nullList, user);
        });
    }

    @Test
    void toEntityList_WhenListContainsNullPhoneDto_ShouldThrowNullPointerException() {
        // Arrange
        List<PhoneDto> listWithNull = Arrays.asList(phoneDto1, null, phoneDto2);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            PhoneMapper.toEntityList(listWithNull, user);
        });
    }

    @Test
    void toEntityList_WhenNullUser_ShouldCreatePhonesWithNullUser() {
        // Arrange
        List<PhoneDto> phoneDtos = Arrays.asList(phoneDto1, phoneDto2);
        User nullUser = null;

        // Act
        List<Phone> result = PhoneMapper.toEntityList(phoneDtos, nullUser);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verificar que todos los teléfonos tienen usuario null
        for (Phone phone : result) {
            assertNotNull(phone);
            assertNull(phone.getUser());
        }
    }

    @Test
    void toEntityList_WhenSinglePhoneDto_ShouldReturnSinglePhoneEntity() {
        // Arrange
        List<PhoneDto> singlePhoneList = Arrays.asList(phoneDto1);

        // Act
        List<Phone> result = PhoneMapper.toEntityList(singlePhoneList, user);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        
        Phone phone = result.get(0);
        assertEquals(phoneDto1.getNumber(), phone.getNumber());
        assertEquals(phoneDto1.getCitycode(), phone.getCityCode());
        assertEquals(phoneDto1.getContrycode(), phone.getContrycode());
        assertEquals(user, phone.getUser());
    }

    @Test
    void toEntityList_WhenPhoneDtoListWithMixedValues_ShouldHandleAllCorrectly() {
        // Arrange
        PhoneDto phoneDtoWithNulls = PhoneDto.builder()
                .number(null)
                .citycode(null)
                .contrycode(null)
                .build();
        
        List<PhoneDto> mixedList = Arrays.asList(phoneDto1, phoneDtoWithNulls, phoneDto2);

        // Act
        List<Phone> result = PhoneMapper.toEntityList(mixedList, user);

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // Verificar primer teléfono (con valores)
        Phone firstPhone = result.get(0);
        assertEquals(phoneDto1.getNumber(), firstPhone.getNumber());
        assertEquals(phoneDto1.getCitycode(), firstPhone.getCityCode());
        assertEquals(phoneDto1.getContrycode(), firstPhone.getContrycode());
        assertEquals(user, firstPhone.getUser());
        
        // Verificar segundo teléfono (con nulls)
        Phone secondPhone = result.get(1);
        assertNull(secondPhone.getNumber());
        assertNull(secondPhone.getCityCode());
        assertNull(secondPhone.getContrycode());
        assertEquals(user, secondPhone.getUser());
        
        // Verificar tercer teléfono (con valores)
        Phone thirdPhone = result.get(2);
        assertEquals(phoneDto2.getNumber(), thirdPhone.getNumber());
        assertEquals(phoneDto2.getCitycode(), thirdPhone.getCityCode());
        assertEquals(phoneDto2.getContrycode(), thirdPhone.getContrycode());
        assertEquals(user, thirdPhone.getUser());
    }
} 