package cl.bci.ejercicio.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String name;
    private String email;
    private List<PhoneDto> phones;
    
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime created;
    
    @JsonFormat(pattern = "MMM dd, yyyy hh:mm:ss a")
    private LocalDateTime lastLogin;
    
    private String token;
    private Boolean isActive;
} 