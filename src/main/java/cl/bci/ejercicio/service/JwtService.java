package cl.bci.ejercicio.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la gestión de tokens JWT.
 * 
 * Esta clase proporciona funcionalidades para generar, validar
 * y extraer información de tokens JWT utilizados para la
 * autenticación de usuarios en el sistema.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class JwtService {

    @Value("${jwt.secret:mySecretKey}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 8 horas por defecto
    private Long jwtExpiration;

    /**
     * Genera un token JWT para el email proporcionado.
     * 
     * Crea un token JWT firmado con el algoritmo HS512, incluyendo:
     * - Subject: email del usuario
     * - Fecha de emisión: momento actual
     * - Fecha de expiración: basada en la configuración
     * 
     * @param email Email del usuario para incluir en el token
     * @return String representando el token JWT generado
     */
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Extrae el email del subject de un token JWT.
     * 
     * Decodifica el token JWT y obtiene el email del usuario
     * almacenado en el campo subject del token.
     * 
     * @param token Token JWT del cual extraer el email
     * @return String representando el email del usuario
     * @throws io.jsonwebtoken.JwtException si el token es inválido o ha expirado
     */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Obtiene los claims (reclamaciones) de un token JWT.
     * 
     * Decodifica y valida el token JWT utilizando la clave secreta
     * configurada, retornando todos los claims contenidos en el token.
     * 
     * @param token Token JWT a decodificar
     * @return Claims objeto conteniendo toda la información del token
     * @throws io.jsonwebtoken.JwtException si el token es inválido, malformado o ha expirado
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
} 