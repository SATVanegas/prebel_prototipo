package com.prebel.prototipo.webapp.models.permissions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String password;
    private String name;
    private String number;
    private String email;
    private Long roleId;
}
