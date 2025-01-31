package com.prebel.prototipo.webapp.models.permissions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequest {
    private Roles roleEnum;
    private List<RoleModuleRequest> modules;
}
