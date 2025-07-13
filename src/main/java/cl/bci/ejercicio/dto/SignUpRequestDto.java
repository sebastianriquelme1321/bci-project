package cl.bci.ejercicio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * DTO para la solicitud de registro de nuevo usuario.
 * 
 * Contiene toda la información necesaria para crear un nuevo usuario
 * en el sistema, incluyendo validaciones de formato para email y contraseña.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@ApiModel(description = "Datos requeridos para el registro de un nuevo usuario")
@Data
public class SignUpRequestDto {

    /**
     * Nombre completo del usuario.
     * Campo obligatorio que no puede estar vacío.
     */
    @ApiModelProperty(value = "Nombre completo del usuario", required = true, example = "Juan Pérez")
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    /**
     * Dirección de correo electrónico del usuario.
     * Debe ser un email válido y único en el sistema.
     */
    @ApiModelProperty(value = "Email del usuario (debe ser único)", required = true, example = "juan.perez@bci.cl")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El formato del email no es válido")
    private String email;

    /**
     * Contraseña del usuario.
     * Debe contener exactamente una mayúscula, dos números, letras minúsculas,
     * con longitud entre 8 y 12 caracteres.
     */
    @ApiModelProperty(
        value = "Contraseña (1 mayúscula, 2 números, 8-12 caracteres)", 
        required = true, 
        example = "Password12"
    )
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:[^\\d]*\\d){2}[^\\d]*$)[A-Za-z\\d]{8,12}$",
            message = " Debe tener solo una Mayúscula y solamente dos números (no necesariamente consecutivos), " +
                    "en combinación de letras minúsculas, largo máximo de 12 y mínimo 8\"")
    private String password;

    /**
     * Lista de teléfonos del usuario.
     * Campo opcional que puede contener múltiples números telefónicos.
     */
    @ApiModelProperty(value = "Lista de teléfonos del usuario", required = false)
    @Valid
    private List<PhoneDto> phones;
}