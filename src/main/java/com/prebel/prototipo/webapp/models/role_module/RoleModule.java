package com.prebel.prototipo.webapp.models.role_module;

import com.prebel.prototipo.webapp.services.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_modules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Convert(converter = StringListConverter.class)
    private String[] permissions;
}
