package com.prebel.prototipo.webapp.models.permissions;

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
