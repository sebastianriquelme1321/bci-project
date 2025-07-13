package cl.bci.ejercicio.controller;

import cl.bci.ejercicio.dto.SignUpRequestDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controlador REST para la gestión de usuarios.
 * 
 * Este controlador proporciona endpoints para el registro de nuevos usuarios
 * y la autenticación mediante tokens JWT. Implementa las operaciones principales
 * del sistema de usuarios de BCI.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@Api(value = "Gestión de Usuarios", tags = {"Usuarios"}, description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/v1/bci")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * Este endpoint permite crear un nuevo usuario validando que:
     * - El email no esté previamente registrado
     * - La contraseña cumpla con el formato requerido (una mayúscula y dos números)
     * - Los datos del usuario sean válidos
     * 
     * @param request Datos del usuario a registrar incluyendo nombre, email, contraseña y teléfonos
     * @return ResponseEntity con los datos del usuario creado y token JWT
     * @throws cl.bci.ejercicio.exception.UserAlReadyExist si el email ya está registrado
     */
    @ApiOperation(
        value = "Registrar nuevo usuario", 
        notes = "Crea un nuevo usuario en el sistema con validaciones de email único y contraseña segura",
        response = SignUpResponseDto.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Usuario creado exitosamente", response = SignUpResponseDto.class),
        @ApiResponse(code = 400, message = "Datos inválidos o email ya existe"),
        @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(
            @ApiParam(value = "Datos del usuario a registrar", required = true)
            @Valid @RequestBody SignUpRequestDto request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signUp(request));
    }

    /**
     * Realiza el login de un usuario mediante token JWT.
     * 
     * Este endpoint permite autenticar a un usuario existente utilizando
     * un token JWT válido. Actualiza la fecha de último login del usuario.
     * 
     * @param token Token JWT válido del usuario
     * @return ResponseEntity con los datos completos del usuario autenticado
     * @throws cl.bci.ejercicio.exception.UserNotFoundException si el usuario no existe
     */
    @ApiOperation(
        value = "Login de usuario", 
        notes = "Autentica un usuario mediante token JWT y actualiza su último login",
        response = UserResponseDto.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Login exitoso", response = UserResponseDto.class),
        @ApiResponse(code = 404, message = "Usuario no encontrado"),
        @ApiResponse(code = 400, message = "Token inválido o malformado"),
        @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(
            @ApiParam(value = "Token JWT del usuario", required = true, example = "eyJhbGciOiJIUzUxMiJ9...")
            @Valid @RequestHeader String token) throws Exception {
        UserResponseDto response = userService.login(token);
        return ResponseEntity.ok(response);
    }
} 