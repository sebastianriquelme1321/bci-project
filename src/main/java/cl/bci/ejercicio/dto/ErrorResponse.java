package cl.bci.ejercicio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime timestamp;
    
    private Integer codigo;
    private String detail;
    
    public ErrorResponse(String detail) {
        this.timestamp = LocalDateTime.now();
        this.detail = detail;
    }
    
    public ErrorResponse(Integer codigo, String detail) {
        this.timestamp = LocalDateTime.now();
        this.codigo = codigo;
        this.detail = detail;
    }
} 