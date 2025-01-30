package com.prebel.prototipo.webapp.models;

import com.prebel.prototipo.webapp.models.tests.*;
import jakarta.persistence.OneToOne;

public class Test {
    private long id;
    private Temperature temperature;
    private Color color;
    private Odor odor;
    private Appearance appearance;
    private pH ph;
    private Viscosity viscosity;
    private SpecificGravity specificGravity;
    private TotalBacteriaCount totalBacteriaCount;
    private FungiYeastCount fungiYeastCount;
    private Pathogens pathogens;
    private Storage storage;
    private User userOrganolepticTests;
    private User userPhysicochemicalTests;
    private String observations;
    private String conclusion;

    public Test(long id, Temperature temperature, Color color, Odor odor, Appearance appearance, pH ph, Viscosity viscosity, SpecificGravity specificGravity, TotalBacteriaCount totalBacteriaCount, FungiYeastCount fungiYeastCount, Pathogens pathogens, Storage storage, User userOrganolepticTests, User userPhysicochemicalTests, String observations, String conclusion) {
        this.id = id;
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
