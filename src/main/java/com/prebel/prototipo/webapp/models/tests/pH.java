package com.prebel.prototipo.webapp.models.tests;

public class pH {

    private long id;
    private String unit;
    private String specification;
    private String method;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;
    private int time; // In weeks
    private int equipment;

    public pH(long id, String unit, String specification, String method, String initialResultsDevelopmentLaboratory, String initialResultsStabilityLaboratory, int time, int equipment) {
        this.id = id;
        this.unit = unit;
        this.specification = specification;
        this.method = method;
        this.initialResultsDevelopmentLaboratory = initialResultsDevelopmentLaboratory;
        this.initialResultsStabilityLaboratory = initialResultsStabilityLaboratory;
        this.time = time;
        this.equipment = equipment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInitialResultsDevelopmentLaboratory() {
        return initialResultsDevelopmentLaboratory;
    }

    public void setInitialResultsDevelopmentLaboratory(String initialResultsDevelopmentLaboratory) {
        this.initialResultsDevelopmentLaboratory = initialResultsDevelopmentLaboratory;
    }

    public String getInitialResultsStabilityLaboratory() {
        return initialResultsStabilityLaboratory;
    }

    public void setInitialResultsStabilityLaboratory(String initialResultsStabilityLaboratory) {
        this.initialResultsStabilityLaboratory = initialResultsStabilityLaboratory;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEquipment() {
        return equipment;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }
}
