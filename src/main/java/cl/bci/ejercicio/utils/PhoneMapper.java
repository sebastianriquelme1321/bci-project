package cl.bci.ejercicio.utils;

import cl.bci.ejercicio.dto.PhoneDto;
import cl.bci.ejercicio.entity.Phone;
import cl.bci.ejercicio.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class PhoneMapper {


    public static Phone toEntity(PhoneDto dto, User user) {
        Phone entity = new Phone();
        entity.setNumber(dto.getNumber());
        entity.setCityCode(dto.getCitycode());
        entity.setContrycode(dto.getContrycode());
        entity.setUser(user);
        return entity;
    }

    public static List<Phone> toEntityList(List<PhoneDto> dtos, User user) {
        return dtos.stream()
                .map(dto -> toEntity(dto, user))
                .collect(Collectors.toList());
    }

}
