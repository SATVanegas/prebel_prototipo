package com.prebel.prototipo.webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserRoleResponseDTO {
    private String name;
    private String roleName;
    private List<RoleModuleDTO> permissions;
}
