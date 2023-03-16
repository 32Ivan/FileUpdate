package com.example.oib.service;

import com.example.oib.model.dto.UserDto;

import java.util.List;

public interface UserService {


    UserDto geUserByOIB(Long OIB);

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(Long OIB, UserDto userDto);

    List<UserDto> findAllUsers();

    void deleteUser(Long oib);
}
