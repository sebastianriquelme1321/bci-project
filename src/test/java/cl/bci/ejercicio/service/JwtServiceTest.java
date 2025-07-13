package cl.bci.ejercicio.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    private final String TEST_SECRET = "testSecretKey";
    private final Long TEST_EXPIRATION = 3600000L; // 1 hora
    private final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        // Arrange - Configuración común
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", TEST_SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", TEST_EXPIRATION);
    }

    @Test
    void generateToken_WhenValidEmail_ShouldReturnValidToken() {
        // Arrange
        String email = TEST_EMAIL;

        // Act
        String token = jwtService.generateToken(email);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.length() > 0);
        
        // Verificar que el token se puede decodificar
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET)
                .parseClaimsJws(token)
                .getBody();
        
        assertEquals(email, claims.getSubject());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void generateToken_WhenNullEmail_ShouldHandleGracefully() {
        // Arrange
        String email = null;

        // Act
        String token = jwtService.generateToken(email);

        // Assert
        assertNotNull(token);
        
        // Verificar que el token se puede decodificar
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET)
                .parseClaimsJws(token)
                .getBody();
        
        assertNull(claims.getSubject());
    }

    @Test
    void generateToken_WhenEmptyEmail_ShouldHandleGracefully() {
        // Arrange
        String email = "";

        // Act
        String token = jwtService.generateToken(email);

        // Assert
        assertNotNull(token);
        
        // Verificar que el token se puede decodificar
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET)
                .parseClaimsJws(token)
                .getBody();
        
        // Empty string is converted to null by JWT
        assertNull(claims.getSubject());
    }

    @Test
    void extractEmail_WhenValidToken_ShouldReturnEmail() {
        // Arrange
        String email = TEST_EMAIL;
        String token = createValidToken(email);

        // Act
        String extractedEmail = jwtService.extractEmail(token);

        // Assert
        assertEquals(email, extractedEmail);
    }

    @Test
    void extractEmail_WhenTokenWithNullSubject_ShouldReturnNull() {
        // Arrange
        String token = createValidToken(null);

        // Act
        String extractedEmail = jwtService.extractEmail(token);

        // Assert
        assertNull(extractedEmail);
    }

    @Test
    void extractEmail_WhenTokenWithEmptySubject_ShouldReturnNull() {
        // Arrange
        String token = createValidToken("");

        // Act
        String extractedEmail = jwtService.extractEmail(token);

        // Assert
        assertNull(extractedEmail);
    }

    @Test
    void extractEmail_WhenInvalidToken_ShouldThrowException() {
        // Arrange
        String invalidToken = "invalid.token.format";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            jwtService.extractEmail(invalidToken);
        });
    }

    @Test
    void extractEmail_WhenExpiredToken_ShouldThrowException() {
        // Arrange
        String expiredToken = Jwts.builder()
                .setSubject(TEST_EMAIL)
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000)) // 2 horas atrás
                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // 1 hora atrás (expirado)
                .signWith(SignatureAlgorithm.HS512, TEST_SECRET)
                .compact();

        // Act & Assert
        assertThrows(Exception.class, () -> {
            jwtService.extractEmail(expiredToken);
        });
    }

    @Test
    void extractEmail_WhenTokenWithWrongSecret_ShouldThrowException() {
        // Arrange
        String tokenWithWrongSecret = Jwts.builder()
                .setSubject(TEST_EMAIL)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TEST_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, "wrongSecret")
                .compact();

        // Act & Assert
        assertThrows(Exception.class, () -> {
            jwtService.extractEmail(tokenWithWrongSecret);
        });
    }

    @Test
    void generateToken_TokenShouldHaveCorrectExpiration() {
        // Arrange
        String email = TEST_EMAIL;
        long currentTime = System.currentTimeMillis();

        // Act
        String token = jwtService.generateToken(email);

        // Assert
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET)
                .parseClaimsJws(token)
                .getBody();

        long tokenExpiration = claims.getExpiration().getTime();
        long expectedExpiration = currentTime + TEST_EXPIRATION;
        
        // Permitir una diferencia de 1 segundo para timing
        assertTrue(Math.abs(tokenExpiration - expectedExpiration) < 1000);
    }

    private String createValidToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TEST_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, TEST_SECRET)
                .compact();
    }
} 