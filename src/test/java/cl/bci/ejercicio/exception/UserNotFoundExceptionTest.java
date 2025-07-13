package cl.bci.ejercicio.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @Test
    void constructor_WhenMessageProvided_ShouldSetMessage() {
        // Arrange
        String expectedMessage = "Usuario no encontrado";

        // Act
        UserNotFoundException exception = new UserNotFoundException(expectedMessage);

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
        UserNotFoundException exception = new UserNotFoundException(nullMessage);

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
        UserNotFoundException exception = new UserNotFoundException(emptyMessage);

        // Assert
        assertEquals("", exception.getMessage());
        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void constructor_WhenCustomMessage_ShouldSetCustomMessage() {
        // Arrange
        String customMessage = "Error personalizado para usuario no encontrado";

        // Act
        UserNotFoundException exception = new UserNotFoundException(customMessage);

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
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(message);
        });
    }

    @Test
    void exception_WhenCaught_ShouldPreserveMessage() {
        // Arrange
        String expectedMessage = "Test exception message";
        UserNotFoundException exception = null;

        // Act
        try {
            throw new UserNotFoundException(expectedMessage);
        } catch (UserNotFoundException e) {
            exception = e;
        }

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }
} 