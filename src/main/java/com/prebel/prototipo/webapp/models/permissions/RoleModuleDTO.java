package com.prebel.prototipo.webapp.models.permissions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleModuleDTO {
    private String module;
    private String[] permissions;
}
