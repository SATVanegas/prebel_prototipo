package com.prebel.prototipo.webapp.models;

import com.prebel.prototipo.webapp.models.permissions.User;
import com.prebel.prototipo.webapp.models.tests.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tests")
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


    public Test(Temperature temperature, Color color, Odor odor, Appearance appearance, pH ph, Viscosity viscosity, SpecificGravity specificGravity, TotalBacteriaCount totalBacteriaCount, FungiYeastCount fungiYeastCount, Pathogens pathogens, Storage storage, User userOrganolepticTests, User userPhysicochemicalTests, String observations, String conclusion) {
        this.temperature = temperature;
        this.color = color;
        this.odor = odor;
        this.appearance = appearance;
        this.ph = ph;
        this.viscosity = viscosity;
        this.specificGravity = specificGravity;
        this.totalBacteriaCount = totalBacteriaCount;
        this.fungiYeastCount = fungiYeastCount;
        this.pathogens = pathogens;
        this.storage = storage;
        this.userOrganolepticTests = userOrganolepticTests;
        this.userPhysicochemicalTests = userPhysicochemicalTests;
        this.observations = observations;
        this.conclusion = conclusion;
    }

    public Test() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Odor getOdor() {
        return odor;
    }

    public void setOdor(Odor odor) {
        this.odor = odor;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    public pH getPh() {
        return ph;
    }

    public void setPh(pH ph) {
        this.ph = ph;
    }

    public Viscosity getViscosity() {
        return viscosity;
    }

    public void setViscosity(Viscosity viscosity) {
        this.viscosity = viscosity;
    }

    public SpecificGravity getSpecificGravity() {
        return specificGravity;
    }

    public void setSpecificGravity(SpecificGravity specificGravity) {
        this.specificGravity = specificGravity;
    }

    public TotalBacteriaCount getTotalBacteriaCount() {
        return totalBacteriaCount;
    }

    public void setTotalBacteriaCount(TotalBacteriaCount totalBacteriaCount) {
        this.totalBacteriaCount = totalBacteriaCount;
    }

    public FungiYeastCount getFungiYeastCount() {
        return fungiYeastCount;
    }

    public void setFungiYeastCount(FungiYeastCount fungiYeastCount) {
        this.fungiYeastCount = fungiYeastCount;
    }

    public Pathogens getPathogens() {
        return pathogens;
    }

    public void setPathogens(Pathogens pathogens) {
        this.pathogens = pathogens;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public User getUserOrganolepticTests() {
        return userOrganolepticTests;
    }

    public void setUserOrganolepticTests(User userOrganolepticTests) {
        this.userOrganolepticTests = userOrganolepticTests;
    }

    public User getUserPhysicochemicalTests() {
        return userPhysicochemicalTests;
    }

    public void setUserPhysicochemicalTests(User userPhysicochemicalTests) {
        this.userPhysicochemicalTests = userPhysicochemicalTests;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
