package com.prebel.prototipo.webapp.models.permissions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModuleRequest {
    private Long moduleId;
    private String[] permissions;
}

