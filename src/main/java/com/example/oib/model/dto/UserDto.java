package com.example.oib.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private Long OIB;
    private Boolean status;

}
