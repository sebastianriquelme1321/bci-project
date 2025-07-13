package cl.bci.ejercicio.exception;

import lombok.Getter;

@Getter
public class UserAlReadyExist extends RuntimeException {
    public UserAlReadyExist(String message) {super(message);}
}

