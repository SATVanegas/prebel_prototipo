package com.prebel.prototipo.webapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleRequestDTO {
    private Long moduleId;
    private String[] permissions;
}

