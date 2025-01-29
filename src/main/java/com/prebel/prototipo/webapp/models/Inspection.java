package com.prebel.prototipo.webapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "inspections")
public class Inspection {
    private Long id;
    private Date expectedDate;
    private Date realDate;
    private int responseTime;
    private int aerosolStove;
    private int inOut;
    private int stove;
    private int hrStove;
    private int environment;
    private int fridge;
    private int photolysis;
}
