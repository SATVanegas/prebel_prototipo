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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Roles getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(Roles roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
