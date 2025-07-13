package cl.bci.ejercicio.utils;

import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.dto.SignUpResponseDto;
import cl.bci.ejercicio.dto.UserResponseDto;
import cl.bci.ejercicio.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {



    public static UserResponseDto convertToUserResponse(User user) throws Exception {
        List<PhoneDto> phoneDtos = user.getPhones().stream()
                .map(phone -> PhoneDto.builder()
                        .number(phone.getNumber())
                        .citycode(phone.getCityCode())
                        .contrycode(phone.getContrycode())
                        .build())
                .collect(Collectors.toList());

        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phones(phoneDtos)
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .password(AESUtil.decrypt(user.getPassword()))
                .isActive(user.getIsActive())
                .build();
    }


    public static SignUpResponseDto convertToSignUpResponse(User user) {

        return SignUpResponseDto.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .token(user.getToken())
                .build();
    }
}
