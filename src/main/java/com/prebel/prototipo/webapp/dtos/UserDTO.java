package com.prebel.prototipo.webapp.dtos;

import com.prebel.prototipo.webapp.models.role_module.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String roleName;

    public UserDTO(User user) {
    }
}
