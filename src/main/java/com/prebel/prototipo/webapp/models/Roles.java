package com.prebel.prototipo.webapp.models;

public enum Roles {
    ADMIN("Full access", "Read, Write, Delete, Manage Users"),
    USER("Limited access", "Read, Write"),
    GUEST("Read-only access", "Read");

    private final String description;
    private final String permissions;

    // Constructor
    Roles(String description, String permissions) {
        this.description = description;
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public String getPermissions() {
        return permissions;
    }
}
