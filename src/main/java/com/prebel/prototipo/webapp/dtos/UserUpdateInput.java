package com.prebel.prototipo.webapp.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateInput {
    private String id;
    private String name;
    private String email;
    private String number;
    private String password;
    private String resetCode;

}

