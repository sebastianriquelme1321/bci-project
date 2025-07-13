package cl.bci.ejercicio.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * DTO que representa la información de un teléfono.
 * 
 * Contiene los datos necesarios para almacenar información
 * telefónica de un usuario, incluyendo código de país y ciudad.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@ApiModel(description = "Información de un teléfono del usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    /**
     * Número telefónico sin códigos de país o ciudad.
     * Campo obligatorio que representa el número local.
     */
    @ApiModelProperty(value = "Número telefónico", required = true, example = "123456789")
    @NotNull(message = "El número es obligatorio")
    private Long number;

    /**
     * Código de ciudad o área telefónica.
     * Campo obligatorio para identificar la región.
     */
    @ApiModelProperty(value = "Código de ciudad", required = true, example = "1")
    @NotNull(message = "El código de ciudad es obligatorio")
    private Integer citycode;

    /**
     * Código de país en formato string.
     * Campo obligatorio para identificar el país del número.
     */
    @ApiModelProperty(value = "Código de país", required = true, example = "57")
    @NotNull(message = "El código de país es obligatorio")
    private String contrycode;
} 