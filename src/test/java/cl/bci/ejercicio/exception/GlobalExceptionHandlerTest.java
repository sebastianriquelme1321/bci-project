package cl.bci.ejercicio.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleUserAlReadyExist_WhenUserAlreadyExistsException_ShouldReturnBadRequest() {
        // Arrange
        String errorMessage = "Ya existe un usuario activo registrado con el mismo email";
        UserAlReadyExist exception = new UserAlReadyExist(errorMessage);

        // Act
        ResponseEntity<ApiErrorResponseDto> response = globalExceptionHandler.handleUserAlReadyExist(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getDetail());
        assertEquals(400, response.getBody().getCodigo());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleUserAlReadyExist_WhenDifferentErrorMessage_ShouldReturnCorrectMessage() {
        // Arrange
        String customErrorMessage = "Error personalizado";
        UserAlReadyExist exception = new UserAlReadyExist(customErrorMessage);

        // Act
        ResponseEntity<ApiErrorResponseDto> response = globalExceptionHandler.handleUserAlReadyExist(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(customErrorMessage, response.getBody().getDetail());
        assertEquals(400, response.getBody().getCodigo());
        assertNotNull(response.getBody().getTimestamp());
    }



    @Test
    void handleConstraintViolationException_WhenSingleViolation_ShouldReturnBadRequest() {
        // Arrange
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Constraint violation message");
        
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);
        
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        // Act
        ResponseEntity<ApiErrorResponseDto> response = globalExceptionHandler.handleConstraintViolationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Constraint violation message", response.getBody().getDetail());
        assertEquals(400, response.getBody().getCodigo());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleConstraintViolationException_WhenMultipleViolations_ShouldReturnConcatenatedMessages() {
        // Arrange
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
        when(violation1.getMessage()).thenReturn("First violation");
        when(violation2.getMessage()).thenReturn("Second violation");
        
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation1);
        violations.add(violation2);
        
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        // Act
        ResponseEntity<ApiErrorResponseDto> response = globalExceptionHandler.handleConstraintViolationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDetail().contains("First violation"));
        assertTrue(response.getBody().getDetail().contains("Second violation"));
        assertEquals(400, response.getBody().getCodigo());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleGenericException_WhenGenericException_ShouldReturnInternalServerError() {
        // Arrange
        Exception exception = new Exception("Unexpected error");

        // Act
        ResponseEntity<ApiErrorResponseDto> response = globalExceptionHandler.handleGenericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ocurrió un error inesperado. Por favor contacte al soporte.", response.getBody().getDetail());
        assertEquals(500, response.getBody().getCodigo());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleGenericException_WhenRuntimeException_ShouldReturnInternalServerError() {
        // Arrange
        RuntimeException exception = new RuntimeException("Runtime error");

        // Act
        ResponseEntity<ApiErrorResponseDto> response = globalExceptionHandler.handleGenericException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ocurrió un error inesperado. Por favor contacte al soporte.", response.getBody().getDetail());
        assertEquals(500, response.getBody().getCodigo());
        assertNotNull(response.getBody().getTimestamp());
    }
} 