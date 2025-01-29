package com.prebel.prototipo.webapp.models;

public class User {
    private long id;
    private String password;
    private String name;
    private String number;
    private String email;

    public User(long id, String password, String name, String number, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
