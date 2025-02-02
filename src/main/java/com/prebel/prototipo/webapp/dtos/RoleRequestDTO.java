package com.prebel.prototipo.webapp.dtos;

import com.prebel.prototipo.webapp.models.role_module.Roles;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequestDTO {
    private Roles roleEnum;
    private List<RoleModuleRequestDTO> modules;
}
