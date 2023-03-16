package com.example.oib.mapper;

import com.example.oib.model.dto.UserDto;
import com.example.oib.model.entity.User;

public class UserMapperUtils {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setStatus(user.getStatus());
        userDto.setOIB(user.getOIB());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setStatus(userDto.getStatus());
        user.setOIB(userDto.getOIB());
        return user;
    }
}
