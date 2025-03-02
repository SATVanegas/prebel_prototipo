package com.prebel.prototipo.webapp.dtos;

import com.prebel.prototipo.webapp.models.role_module.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String roleName;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roleName = user.getRole().getRoleName();
    }
}
