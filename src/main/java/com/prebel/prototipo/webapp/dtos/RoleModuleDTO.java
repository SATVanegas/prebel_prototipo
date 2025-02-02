package com.prebel.prototipo.webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class  RoleModuleDTO {
    private String module;
    private String[] permissions;
}
