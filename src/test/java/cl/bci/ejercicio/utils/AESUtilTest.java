package cl.bci.ejercicio.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase AESUtil.
 * 
 * Verifica el correcto funcionamiento de los métodos de encriptación
 * y desencriptación AES utilizados en el sistema.
 */
class AESUtilTest {

    @Test
    void encrypt_WhenValidString_ShouldReturnEncryptedString() throws Exception {
        // Arrange
        String plainText = "testPassword123";

        // Act
        String encryptedText = AESUtil.encrypt(plainText);

        // Assert
        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText);
        assertTrue(encryptedText.length() > 0);
    }

    @Test
    void decrypt_WhenValidEncryptedString_ShouldReturnOriginalString() throws Exception {
        // Arrange
        String originalText = "testPassword123";
        String encryptedText = AESUtil.encrypt(originalText);

        // Act
        String decryptedText = AESUtil.decrypt(encryptedText);

        // Assert
        assertNotNull(decryptedText);
        assertEquals(originalText, decryptedText);
    }

    @Test
    void encryptAndDecrypt_WhenEmptyString_ShouldHandleCorrectly() throws Exception {
        // Arrange
        String emptyString = "";

        // Act
        String encrypted = AESUtil.encrypt(emptyString);
        String decrypted = AESUtil.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotNull(decrypted);
        assertEquals(emptyString, decrypted);
    }

    @Test
    void encryptAndDecrypt_WhenSpecialCharacters_ShouldHandleCorrectly() throws Exception {
        // Arrange
        String specialText = "!@#$%^&*()_+{}[]|:;\"'<>,.?/~`";

        // Act
        String encrypted = AESUtil.encrypt(specialText);
        String decrypted = AESUtil.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotNull(decrypted);
        assertEquals(specialText, decrypted);
    }

    @Test
    void encryptAndDecrypt_WhenUnicodeCharacters_ShouldHandleCorrectly() throws Exception {
        // Arrange
        String unicodeText = "Hola España niños café";

        // Act
        String encrypted = AESUtil.encrypt(unicodeText);
        String decrypted = AESUtil.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotNull(decrypted);
        assertEquals(unicodeText, decrypted);
    }

    @Test
    void encryptAndDecrypt_WhenLongString_ShouldHandleCorrectly() throws Exception {
        // Arrange
        String longText = "This is a very long string that contains multiple words and should be encrypted and decrypted correctly without any issues";

        // Act
        String encrypted = AESUtil.encrypt(longText);
        String decrypted = AESUtil.decrypt(encrypted);

        // Assert
        assertNotNull(encrypted);
        assertNotNull(decrypted);
        assertEquals(longText, decrypted);
    }

    @Test
    void encrypt_WhenSameInputTwice_ShouldReturnSameOutput() throws Exception {
        // Arrange
        String plainText = "consistentTest";

        // Act
        String encrypted1 = AESUtil.encrypt(plainText);
        String encrypted2 = AESUtil.encrypt(plainText);

        // Assert
        assertNotNull(encrypted1);
        assertNotNull(encrypted2);
        assertEquals(encrypted1, encrypted2);
    }

    @Test
    void decrypt_WhenInvalidBase64_ShouldThrowException() {
        // Arrange
        String invalidBase64 = "invalid-base64-string!@#";

        // Act & Assert
        assertThrows(Exception.class, () -> {
            AESUtil.decrypt(invalidBase64);
        });
    }

    @Test
    void decrypt_WhenNullString_ShouldThrowException() {
        // Arrange
        String nullString = null;

        // Act & Assert
        assertThrows(Exception.class, () -> {
            AESUtil.decrypt(nullString);
        });
    }

    @Test
    void encrypt_WhenNullString_ShouldThrowException() {
        // Arrange
        String nullString = null;

        // Act & Assert
        assertThrows(Exception.class, () -> {
            AESUtil.encrypt(nullString);
        });
    }

    @Test
    void encryptAndDecrypt_WhenMultipleOperations_ShouldBeConsistent() throws Exception {
        // Arrange
        String[] testStrings = {
            "password1",
            "Password123",
            "test@example.com",
            "短いテキスト",
            "123456789",
            "MixedCase123!@#"
        };

        // Act & Assert
        for (String testString : testStrings) {
            String encrypted = AESUtil.encrypt(testString);
            String decrypted = AESUtil.decrypt(encrypted);
            
            assertEquals(testString, decrypted, 
                "Failed to correctly encrypt/decrypt: " + testString);
        }
    }
} 