package com.prebel.prototipo.webapp.dtos.validations;

import com.prebel.prototipo.webapp.dtos.RoleModuleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequestDTO {
    private String roleName;
    private String description;
    private List<RoleModuleDTO> modules;
    private List<String> modulesToRemove;
}
