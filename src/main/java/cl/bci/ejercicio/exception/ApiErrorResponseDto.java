package cl.bci.ejercicio.exception;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Builder
public class ApiErrorResponseDto {
    Timestamp timestamp;
    Integer codigo;
    String detail;
}
