package com.prebel.prototipo.webapp.models.tests;

public class Pathogens {

    private long id;
    private String unit;
    private String specification;
    private String method;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;
    private int time; // In weeks
    private int equipment;

    public Pathogens(long id, String unit, String specification, String method, String initialResultsDevelopmentLaboratory, String initialResultsStabilityLaboratory, int time, int equipment) {
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

    public String getUnit() {
        return unit;
    }

    public String getSpecification() {
        return specification;
    }

    public String getMethod() {
        return method;
    }

    public String getInitialResultsDevelopmentLaboratory() {
        return initialResultsDevelopmentLaboratory;
    }

    public String getInitialResultsStabilityLaboratory() {
        return initialResultsStabilityLaboratory;
    }

    public int getTime() {
        return time;
    }

    public int getEquipment() {
        return equipment;
    }
}
