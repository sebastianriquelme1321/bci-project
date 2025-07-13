package cl.bci.ejercicio.service;

import cl.bci.ejercicio.dto.*;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;
import cl.bci.ejercicio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ValidationService validationService;

    @Transactional
    public UserResponse signUp(SignUpRequest request) {
        // Validar formato de email y contraseña
        validationService.validateEmail(request.getEmail());
        validationService.validatePassword(request.getPassword());

        // Verificar si el usuario ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo ya registrado");
        }

        // Crear el usuario
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .build();

        // Crear los teléfonos si existen
        if (request.getPhones() != null) {
            List<Phone> phones = request.getPhones().stream()
                    .map(phoneDto -> Phone.builder()
                            .number(phoneDto.getNumber())
                            .citycode(phoneDto.getCitycode())
                            .contrycode(phoneDto.getContrycode())
                            .user(user)
                            .build())
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }

        // Generar token JWT
        String token = jwtService.generateToken(user.getEmail());
        user.setToken(token);

        // Guardar en la base de datos
        User savedUser = userRepository.save(user);

        // Convertir a DTO de respuesta
        return convertToUserResponse(savedUser);
    }

    @Transactional
    public UserResponse login(LoginRequest request) {
        // Buscar usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario y/o contraseña inválidos"));

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Usuario y/o contraseña inválidos");
        }

        // Verificar si el usuario está activo
        if (!user.getIsActive()) {
            throw new IllegalArgumentException("Usuario inactivo");
        }

        // Generar nuevo token JWT
        String token = jwtService.generateToken(user.getEmail());
        user.setToken(token);
        user.setLastLogin(LocalDateTime.now());

        // Guardar cambios
        User savedUser = userRepository.save(user);

        // Convertir a DTO de respuesta
        return convertToUserResponse(savedUser);
    }

    private UserResponse convertToUserResponse(User user) {
        List<PhoneDto> phoneDtos = user.getPhones().stream()
                .map(phone -> PhoneDto.builder()
                        .number(phone.getNumber())
                        .citycode(phone.getCitycode())
                        .contrycode(phone.getContrycode())
                        .build())
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phones(phoneDtos)
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .build();
    }
} 