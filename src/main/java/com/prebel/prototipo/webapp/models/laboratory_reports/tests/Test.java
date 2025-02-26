package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
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
    @JoinColumn(name = "test_id")
    private List<Condition> conditions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_organoleptic_tests_id")
    private User userOrganolepticTests;

    @ManyToOne
    @JoinColumn(name = "user_physicochemical_tests_id")
    private User userPhysicochemicalTests;

    private String observations;
    private String conclusion;

    public Test(TestDTO dto,List<Condition> conditions, Product product, Temperature temperature, Storage storage,
                User userOrganolepticTests, User userPhysicochemicalTests) {
        this.conditions = conditions;
        this.product = product;
        this.temperature = temperature;
        this.storage = storage;
        this.userOrganolepticTests = userOrganolepticTests;
        this.userPhysicochemicalTests = userPhysicochemicalTests;
        this.observations = dto.getObservations();
        this.conclusion = dto.getConclusion();
    }
}
