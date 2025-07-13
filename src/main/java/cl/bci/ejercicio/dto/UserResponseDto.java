package cl.bci.ejercicio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO de respuesta con información completa del usuario.
 * 
 * Contiene todos los datos del usuario después de una operación
 * exitosa de login, incluyendo información personal y de contacto.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@ApiModel(description = "Información completa del usuario autenticado")
@Data
@Builder
public class UserResponseDto {

    /**
     * Identificador único del usuario en el sistema.
     */
    @ApiModelProperty(value = "ID único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    /**
     * Fecha y hora de creación del usuario.
     */
    @ApiModelProperty(value = "Fecha de creación del usuario", example = "2023-12-07T10:30:00")
    private LocalDateTime created;

    /**
     * Fecha y hora del último login del usuario.
     * Se actualiza en cada login exitoso.
     */
    @ApiModelProperty(value = "Fecha del último login", example = "2023-12-07T15:45:00")
    private LocalDateTime lastLogin;

    /**
     * Token JWT actualizado para el usuario.
     */
    @ApiModelProperty(value = "Token JWT de autenticación", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String token;

    /**
     * Estado de activación del usuario.
     */
    @ApiModelProperty(value = "Estado activo del usuario", example = "true")
    private Boolean isActive;

    /**
     * Nombre completo del usuario.
     */
    @ApiModelProperty(value = "Nombre completo del usuario", example = "Juan Pérez")
    private String name;

    /**
     * Dirección de correo electrónico del usuario.
     */
    @ApiModelProperty(value = "Email del usuario", example = "juan.perez@bci.cl")
    private String email;

    /**
     * Contraseña del usuario (normalmente no debería exponerse).
     */
    @ApiModelProperty(value = "Contraseña del usuario", example = "Password12")
    private String password;

    /**
     * Lista de teléfonos asociados al usuario.
     */
    @ApiModelProperty(value = "Lista de teléfonos del usuario")
    private List<PhoneDto> phones;
} 