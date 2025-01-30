package com.prebel.prototipo.webapp.models.tests;

import jakarta.persistence.*;

@Entity
@Table(name = "storages")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int maxTemperature; //In Celcius
    private int minTemperature; //In Celcius
    private String equipmentCode;
    private String description;

    public Storage(int maxTemperature, int minTemperature, String equipmentCode, String description) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.equipmentCode = equipmentCode;
        this.description = description;
    }

    public Storage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
