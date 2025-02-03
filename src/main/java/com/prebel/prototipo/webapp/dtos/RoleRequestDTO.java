package com.prebel.prototipo.webapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequestDTO {
    private String roleName;
    private List<RoleModuleRequestDTO> modules;
}
