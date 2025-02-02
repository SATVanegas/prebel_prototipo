package com.prebel.prototipo.webapp.models;

import com.prebel.prototipo.webapp.models.role_module.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
