package cl.bci.ejercicio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlReadyExist.class)
    public ResponseEntity<ApiErrorResponseDto> handleUserAlReadyExist(UserAlReadyExist ex) {

        ApiErrorResponseDto errorResponseDto = ApiErrorResponseDto.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .detail(ex.getMessage())
                .codigo(HttpStatus.BAD_REQUEST.value())
                .build();
        
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiErrorResponseDto errorResponseDto = ApiErrorResponseDto.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .detail(errorMessage)
                .codigo(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponseDto> handleConstraintViolationException(ConstraintViolationException ex) {

        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(", "));

        ApiErrorResponseDto errorResponseDto = ApiErrorResponseDto.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .detail(errorMessage)
                .codigo(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponseDto> handleGenericException(Exception ex) {



        ApiErrorResponseDto errorResponseDto = ApiErrorResponseDto.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .detail("Ocurrió un error inesperado. Por favor contacte al soporte.")
                .codigo(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }
} 