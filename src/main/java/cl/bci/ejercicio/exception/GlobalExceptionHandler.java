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

/**
 * Manejador global de excepciones para la aplicación.
 * 
 * Esta clase intercepta y maneja todas las excepciones que ocurren
 * en los controladores REST, proporcionando respuestas consistentes
 * y estructuradas para diferentes tipos de errores.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones cuando un usuario ya existe en el sistema.
     * 
     * Se ejecuta cuando se intenta registrar un usuario con un email
     * que ya está registrado en la base de datos.
     * 
     * @param ex Excepción UserAlReadyExist lanzada
     * @return ResponseEntity con código 400 y mensaje de error
     */
    @ExceptionHandler(UserAlReadyExist.class)
    public ResponseEntity<ApiErrorResponseDto> handleUserAlReadyExist(UserAlReadyExist ex) {

        ApiErrorResponseDto errorResponseDto = ApiErrorResponseDto.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .detail(ex.getMessage())
                .codigo(HttpStatus.BAD_REQUEST.value())
                .build();
        
        return ResponseEntity.badRequest().body(errorResponseDto);
    }

    /**
     * Maneja excepciones de validación de argumentos de métodos.
     * 
     * Se ejecuta cuando fallan las validaciones de Bean Validation
     * en los DTOs de entrada de los controladores.
     * 
     * @param ex Excepción MethodArgumentNotValidException con detalles de validación
     * @return ResponseEntity con código 400 y mensajes de validación concatenados
     */
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

    /**
     * Maneja excepciones de violación de restricciones.
     * 
     * Se ejecuta cuando se violan restricciones de validación
     * a nivel de base de datos o validaciones customizadas.
     * 
     * @param ex Excepción ConstraintViolationException con las violaciones
     * @return ResponseEntity con código 400 y mensajes de violación concatenados
     */
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

    /**
     * Maneja todas las excepciones genéricas no capturadas por otros manejadores.
     * 
     * Este es el manejador de último recurso que captura cualquier excepción
     * no prevista y devuelve un mensaje genérico de error interno.
     * 
     * @param ex Excepción genérica capturada
     * @return ResponseEntity con código 500 y mensaje genérico de error
     */
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