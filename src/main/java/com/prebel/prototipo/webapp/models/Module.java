package com.prebel.prototipo.webapp.models;

import com.prebel.prototipo.webapp.services.StringListConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;
    @Convert(converter = StringListConverter.class)
    private String[] permissions;
    private String description;

    public Module() {
    }

    public Module(long id, String[] permissions, String description) {
        this.id = id;
        this.permissions = permissions;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
