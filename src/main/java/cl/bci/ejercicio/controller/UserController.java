package cl.bci.ejercicio.controller;

import cl.bci.ejercicio.dto.LoginRequestDto;
import cl.bci.ejercicio.dto.SignUpRequestDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/bci")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        UserResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }
} 