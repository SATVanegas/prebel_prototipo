package com.prebel.prototipo.webapp.dtos;

import com.prebel.prototipo.webapp.models.role_module.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String password;
    private String name;
    private String number;
    private String email;
    private Roles roleEnum;
}
