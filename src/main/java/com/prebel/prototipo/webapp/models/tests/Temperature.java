package com.prebel.prototipo.webapp.models.tests;

public class Temperature {

    private long id;
    private String unit;
    private int time; // In weeks
    private int equipment;

    public Temperature(long id, String unit, int time, int equipment) {
        this.id = id;
        this.unit = unit;
        this.time = time;
        this.equipment = equipment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
