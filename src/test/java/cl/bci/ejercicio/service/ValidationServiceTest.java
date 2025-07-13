package cl.bci.ejercicio.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @InjectMocks
    private ValidationService validationService;

    @Test
    void testValidEmail() {
        assertTrue(validationService.isValidEmail("test@example.com"));
        assertTrue(validationService.isValidEmail("user.name@domain.co.uk"));
        assertTrue(validationService.isValidEmail("user+tag@example.org"));
    }

    @Test
    void testInvalidEmail() {
        assertFalse(validationService.isValidEmail("invalid-email"));
        assertFalse(validationService.isValidEmail("@example.com"));
        assertFalse(validationService.isValidEmail("user@"));
        assertFalse(validationService.isValidEmail("user@.com"));
        assertFalse(validationService.isValidEmail(null));
    }

    @Test
    void testValidPassword() {
        assertTrue(validationService.isValidPassword("Password12"));
        assertTrue(validationService.isValidPassword("Test1234"));
        assertTrue(validationService.isValidPassword("MyPass12"));
    }

    @Test
    void testInvalidPassword() {
        assertFalse(validationService.isValidPassword("password12")); // Sin mayúscula
        assertFalse(validationService.isValidPassword("PASSWORD12")); // Sin minúscula
        assertFalse(validationService.isValidPassword("Password1")); // Solo un número
        assertFalse(validationService.isValidPassword("Password123")); // Tres números
        assertFalse(validationService.isValidPassword("Pass12")); // Muy corto
        assertFalse(validationService.isValidPassword("VeryLongPassword12345")); // Muy largo
        assertFalse(validationService.isValidPassword(null));
    }

    @Test
    void testValidateEmail_Success() {
        assertDoesNotThrow(() -> validationService.validateEmail("test@example.com"));
    }

    @Test
    void testValidateEmail_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.validateEmail("invalid-email")
        );
        assertEquals("El correo debe seguir una expresión regular para validar que formato sea el correcto", exception.getMessage());
    }

    @Test
    void testValidatePassword_Success() {
        assertDoesNotThrow(() -> validationService.validatePassword("Password12"));
    }

    @Test
    void testValidatePassword_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validationService.validatePassword("invalid")
        );
        assertEquals("La clave debe seguir una expresión regular para validar que formato sea el correcto. Debe tener solo una Mayúscula y solamente dos números (no necesariamente consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8", exception.getMessage());
    }
} 