package cl.bci.ejercicio.service;

import cl.bci.ejercicio.dto.SignUpRequestDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;
import cl.bci.ejercicio.exception.UserAlReadyExist;
import cl.bci.ejercicio.exception.UserNotFoundException;
import cl.bci.ejercicio.repository.UserRepository;
import cl.bci.ejercicio.utils.AESUtil;
import cl.bci.ejercicio.utils.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static cl.bci.ejercicio.utils.UserMapper.convertToSignUpResponse;
import static cl.bci.ejercicio.utils.UserMapper.convertToUserResponse;

/**
 * Servicio para la gestión de usuarios del sistema.
 * 
 * Esta clase contiene la lógica de negocio para las operaciones
 * relacionadas con usuarios, incluyendo registro, autenticación
 * y validaciones de negocio.
 * 
 * @author BCI Team
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;


    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * Este método realiza las siguientes operaciones:
     * 1. Valida que el email no esté previamente registrado
     * 2. Crea el usuario con los datos proporcionados
     * 3. Mapea los teléfonos si están presentes
     * 4. Genera un token JWT para el usuario
     * 5. Persiste el usuario en la base de datos
     * 
     * @param request DTO con los datos del usuario a registrar
     * @return SignUpResponseDto con la información básica del usuario creado y su token
     * @throws UserAlReadyExist si ya existe un usuario con el mismo email
     */
    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto request) throws Exception {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlReadyExist("Ya existe un usuario activo registrado con el mismo email");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(AESUtil.encrypt(request.getPassword()))
                .isActive(true)
                .build();

        if (request.getPhones() != null) {
            List<Phone> phones = PhoneMapper.toEntityList(request.getPhones(), user);
            user.setPhones(phones);
        }

        String token = jwtService.generateToken(user.getEmail());
        user.setToken(token);

        User savedUser = userRepository.save(user);

        return convertToSignUpResponse(savedUser);
    }

    /**
     * Autentica un usuario mediante token JWT.
     * 
     * Este método realiza las siguientes operaciones:
     * 1. Extrae el email del token JWT proporcionado
     * 2. Busca el usuario en la base de datos por email
     * 3. Actualiza la fecha de último login
     * 4. Retorna la información completa del usuario
     * 
     * @param token Token JWT válido del usuario
     * @return UserResponseDto con toda la información del usuario autenticado
     * @throws UserNotFoundException si no se encuentra un usuario con el email del token
     */
    @Transactional
    public UserResponseDto login(String token) throws Exception {
        String email = jwtService.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return convertToUserResponse(user);
    }

} 