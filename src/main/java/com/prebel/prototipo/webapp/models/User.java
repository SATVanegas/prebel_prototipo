package com.prebel.prototipo.webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String password;
    private String name;
    private String number;
    private String email;
    private String resetCode;

    // Relación ManyToOne, ya que un usuario tiene un solo rol
    @ManyToOne
    @JoinColumn(name = "role_id")  // La columna "role_id" se crea en la tabla "users"
    private Role role;

    public User() {
    }

    public User(long id, String password, String name, String number, String email, Role role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.number = number;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getResetCode() {return resetCode;}

    public void setResetCode(String resetCode) {this.resetCode = resetCode;}
}
