package com.prebel.prototipo.webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private  Roles roleEnum; // "ADMIN", "USER", "GUEST"
    private String description;
    private String permissions;

    public Role() {
    }

    public Role(long id, Roles roleEnum, String description, String permissions) {
        this.id = id;
        this.roleEnum = roleEnum;
        this.description = roleEnum.getDescription();
        this.permissions = roleEnum.getPermissions();
    }


}
