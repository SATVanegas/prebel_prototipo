package com.prebel.prototipo.webapp.dtos;

import com.prebel.prototipo.webapp.models.role_module.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private Roles roleEnum;
    private String name;
}
