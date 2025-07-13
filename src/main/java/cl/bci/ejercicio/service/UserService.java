package cl.bci.ejercicio.service;

import cl.bci.ejercicio.dto.*;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;
import cl.bci.ejercicio.exception.UserAlReadyExist;
import cl.bci.ejercicio.exception.UserNotFoundException;
import cl.bci.ejercicio.repository.UserRepository;
import cl.bci.ejercicio.utils.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cl.bci.ejercicio.utils.UserMapper.convertToSignUpResponse;
import static cl.bci.ejercicio.utils.UserMapper.convertToUserResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;


    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlReadyExist("Ya existe un usuario activo registrado con el mismo email");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
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

    @Transactional
    public UserResponseDto login(String token) {
        String email = jwtService.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return convertToUserResponse(user);
    }

} 