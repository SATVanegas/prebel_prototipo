package com.prebel.prototipo.webapp.models.laboratory_reports;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "temperature_id", referencedColumnName = "id")
    private Temperature temperature;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "color_id", referencedColumnName = "id")
    private Color color;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "odor_id", referencedColumnName = "id")
    private Odor odor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appearance_id", referencedColumnName = "id")
    private Appearance appearance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ph_id", referencedColumnName = "id")
    private pH ph;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "viscosity_id", referencedColumnName = "id")
    private Viscosity viscosity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specific_gravity_id", referencedColumnName = "id")
    private SpecificGravity specificGravity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "total_bacteria_count_id", referencedColumnName = "id")
    private TotalBacteriaCount totalBacteriaCount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fungi_yeast_count_id", referencedColumnName = "id")
    private FungiYeastCount fungiYeastCount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pathogens_id", referencedColumnName = "id")
    private Pathogens pathogens;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "user_organoleptic_tests_id")
    private User userOrganolepticTests;

    @ManyToOne
    @JoinColumn(name = "user_physicochemical_tests_id")
    private User userPhysicochemicalTests;

    private String observations;
    private String conclusion;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}