package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.GetProductDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
    @JoinColumn(name = "product_id")
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


    public Test(TestDTO dto, Product product, Temperature temperature, Storage storage) {
        this.product = product;
        this.temperature = temperature;
        this.storage = storage;
        this.observations = dto.getObservations();
        this.conclusion = dto.getConclusion();
    }

    public Test(@Valid TestDTO dto, GetProductDTO product, Temperature temperature, Storage storage) {
    }
}
