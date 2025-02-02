package com.prebel.prototipo.webapp.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class  RoleModuleDTO {
    private String module;
    private String[] permissions;
}
