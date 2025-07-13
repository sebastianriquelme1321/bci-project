package cl.bci.ejercicio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de respuesta para el registro exitoso de un usuario.
 * 
 * Contiene la información básica del usuario recién creado,
 * incluyendo su identificador único y token de autenticación.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@ApiModel(description = "Respuesta del registro exitoso de usuario")
@Data
@Builder
public class SignUpResponseDto {

    /**
     * Identificador único del usuario en el sistema.
     * Generado automáticamente como UUID.
     */
    @ApiModelProperty(value = "ID único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    /**
     * Fecha y hora de creación del usuario.
     * Se establece automáticamente al momento del registro.
     */
    @ApiModelProperty(value = "Fecha de creación del usuario", example = "2023-12-07T10:30:00")
    private LocalDateTime created;

    /**
     * Fecha y hora del último login del usuario.
     * Inicialmente igual a la fecha de creación.
     */
    @ApiModelProperty(value = "Fecha del último login", example = "2023-12-07T10:30:00")
    private LocalDateTime lastLogin;

    /**
     * Token JWT para autenticación del usuario.
     * Debe ser enviado en las peticiones autenticadas.
     */
    @ApiModelProperty(value = "Token JWT de autenticación", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String token;

    /**
     * Estado de activación del usuario.
     * Los usuarios nuevos se crean activos por defecto.
     */
    @ApiModelProperty(value = "Estado activo del usuario", example = "true")
    private Boolean isActive;
}
