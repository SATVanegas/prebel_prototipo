package com.prebel.prototipo.webapp.models.laboratory_reports;

import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "temperature_id", referencedColumnName = "id")
    private Temperature temperature;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private Storage storage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "test_id") // Clave foránea en Condition
    private List<Condition> conditions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_organoleptic_tests_id")
    private User userOrganolepticTests;

    @ManyToOne
    @JoinColumn(name = "user_physicochemical_tests_id")
    private User userPhysicochemicalTests;

    private String observations;
    private String conclusion;
}
