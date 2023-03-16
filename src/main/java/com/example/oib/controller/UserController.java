package com.example.oib.controller;

import com.example.oib.model.dto.UserDto;
import com.example.oib.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping(value = "find-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> userFindAll() {
        try {
            return ResponseEntity.ok(this.userService.findAllUsers());
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "OIB/{OIB}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> userOib(@PathVariable Long OIB) {
        try {
            return ResponseEntity.ok(userService.geUserByOIB(OIB));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/{OIB}")
    public ResponseEntity<UserDto> userUpdate(@PathVariable Long OIB, @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(OIB, userDto);
            return ResponseEntity.ok(updatedUser);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("save")
    public ResponseEntity<UserDto> userSave(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.saveUser(userDto));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("delete/{OIB}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long OIB) {
        try {
            this.userService.deleteUser(OIB);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
