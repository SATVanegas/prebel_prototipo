package com.prebel.prototipo.webapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleRequestDTO {
    private String moduleName;
    private String[] permissions;
}

