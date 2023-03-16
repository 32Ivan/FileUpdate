package com.example.oib.service;

import com.example.oib.mapper.UserMapperUtils;
import com.example.oib.model.dto.UserDto;
import com.example.oib.model.entity.User;
import com.example.oib.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final FileManagement fileManagement;

    @Override
    public UserDto geUserByOIB(Long OIB) {
        return UserMapperUtils.toDto(userRepository.findByOIB(OIB).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Problem with OIB")));
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if( userRepository.findByOIB(userDto.getOIB()).isPresent() || userDto.getOIB().toString().length() != 10 )  {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exist or OIB size not valid");
        }
        User user = UserMapperUtils.toEntity(userDto);
        fileManagement.createFile(user);
       return UserMapperUtils.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long OIB, UserDto userDto) {
        return userRepository.findByOIB(OIB)
                .map(existingTariff -> {
                    User updatedTariff = UserMapperUtils.toEntity(userDto);
                    updatedTariff.setId(existingTariff.getId());
                    User saveUser = userRepository.save(updatedTariff);
                    fileManagement.updateUserInFile(saveUser);
                    return UserMapperUtils.toDto(saveUser);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Something went wrong check all fields"));
    }


    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(UserMapperUtils::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long OIB) {
        User user = userRepository.findByOIB(OIB).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Problem with OIB"));
        userRepository.delete(user);
        fileManagement.statusOfFile(user);
    }


}
