package cl.bci.ejercicio.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{2})[a-zA-Z\\d]{8,12}$";

    private final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

    public boolean isValidEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        return password != null && passwordPattern.matcher(password).matches();
    }

    public void validateEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("El correo debe seguir una expresión regular para validar que formato sea el correcto");
        }
    }

    public void validatePassword(String password) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("La clave debe seguir una expresión regular para validar que formato sea el correcto. Debe tener solo una Mayúscula y solamente dos números (no necesariamente consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8");
        }
    }
} 