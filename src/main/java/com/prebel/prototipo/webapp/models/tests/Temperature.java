package com.prebel.prototipo.webapp.models.tests;

import jakarta.persistence.*;

@Entity
@Table(name = "temperatures")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String unit;
    private int time; // In weeks
    private int equipment;

    public Temperature(String unit, int time, int equipment) {
        this.unit = unit;
        this.time = time;
        this.equipment = equipment;
    }

    public Temperature() {
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
