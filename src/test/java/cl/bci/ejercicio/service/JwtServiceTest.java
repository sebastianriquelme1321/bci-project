package cl.bci.ejercicio.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Test
    void testGenerateToken() {
        // Arrange
        ReflectionTestUtils.setField(jwtService, "secret", "mySecretKeyForJWTTokenGenerationAndValidation");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);
        
        String email = "test@example.com";

        // Act
        String token = jwtService.generateToken(email);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void testExtractEmail() {
        // Arrange
        ReflectionTestUtils.setField(jwtService, "secret", "mySecretKeyForJWTTokenGenerationAndValidation");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);
        
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        // Act
        String extractedEmail = jwtService.extractEmail(token);

        // Assert
        assertEquals(email, extractedEmail);
    }

    @Test
    void testIsTokenValid() {
        // Arrange
        ReflectionTestUtils.setField(jwtService, "secret", "mySecretKeyForJWTTokenGenerationAndValidation");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);
        
        String email = "test@example.com";
        String token = jwtService.generateToken(email);

        // Act & Assert
        assertTrue(jwtService.isTokenValid(token, email));
        assertFalse(jwtService.isTokenValid(token, "different@example.com"));
    }

    @Test
    void testGetExpirationTime() {
        // Arrange
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);

        // Act
        long expirationTime = jwtService.getExpirationTime();

        // Assert
        assertEquals(86400000L, expirationTime);
    }
} 