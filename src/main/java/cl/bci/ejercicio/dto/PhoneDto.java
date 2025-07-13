package cl.bci.ejercicio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    @NotNull(message = "El número es obligatorio")
    private Long number;

    @NotNull(message = "El código de ciudad es obligatorio")
    private Integer citycode;

    @NotNull(message = "El código de país es obligatorio")
    private String contrycode;
} 