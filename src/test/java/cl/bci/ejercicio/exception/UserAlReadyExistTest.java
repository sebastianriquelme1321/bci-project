package cl.bci.ejercicio.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlReadyExistTest {

    @Test
    void constructor_WhenMessageProvided_ShouldSetMessage() {
        // Arrange
        String expectedMessage = "Ya existe un usuario activo registrado con el mismo email";

        // Act
        UserAlReadyExist exception = new UserAlReadyExist(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WhenNullMessage_ShouldAcceptNull() {
        // Arrange
        String nullMessage = null;

        // Act
        UserAlReadyExist exception = new UserAlReadyExist(nullMessage);

        // Assert
        assertNull(exception.getMessage());
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WhenEmptyMessage_ShouldAcceptEmptyString() {
        // Arrange
        String emptyMessage = "";

        // Act
        UserAlReadyExist exception = new UserAlReadyExist(emptyMessage);

        // Assert
        assertEquals("", exception.getMessage());
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WhenCustomMessage_ShouldSetCustomMessage() {
        // Arrange
        String customMessage = "Error personalizado para usuario existente";

        // Act
        UserAlReadyExist exception = new UserAlReadyExist(customMessage);

        // Assert
        assertEquals(customMessage, exception.getMessage());
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void exception_WhenThrown_ShouldBeRuntimeException() {
        // Arrange
        String message = "Test message";

        // Act & Assert
        assertThrows(UserAlReadyExist.class, () -> {
            throw new UserAlReadyExist(message);
        });
    }

    @Test
    void exception_WhenCaught_ShouldPreserveMessage() {
        // Arrange
        String expectedMessage = "Test exception message";
        UserAlReadyExist exception = null;

        // Act
        try {
            throw new UserAlReadyExist(expectedMessage);
        } catch (UserAlReadyExist e) {
            exception = e;
        }

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }
} 