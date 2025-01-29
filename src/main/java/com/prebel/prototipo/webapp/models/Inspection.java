package com.prebel.prototipo.webapp.models;
import java.util.Date;


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


    public Inspection(Long id, Date expectedDate, Date realDate, int responseTime, int aerosolStove, int inOut, int stove, int hrStove, int environment, int fridge, int photolysis) {
        this.id = id;
        this.expectedDate = expectedDate;
        this.realDate = realDate;
        this.responseTime = responseTime;
        this.aerosolStove = aerosolStove;
        this.inOut = inOut;
        this.stove = stove;
        this.hrStove = hrStove;
        this.environment = environment;
        this.fridge = fridge;
        this.photolysis = photolysis;
    }

    public Inspection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getRealDate() {
        return realDate;
    }

    public void setRealDate(Date realDate) {
        this.realDate = realDate;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getAerosolStove() {
        return aerosolStove;
    }

    public void setAerosolStove(int aerosolStove) {
        this.aerosolStove = aerosolStove;
    }

    public int getInOut() {
        return inOut;
    }

    public void setInOut(int inOut) {
        this.inOut = inOut;
    }

    public int getStove() {
        return stove;
    }

    public void setStove(int stove) {
        this.stove = stove;
    }

    public int getHrStove() {
        return hrStove;
    }

    public void setHrStove(int hrStove) {
        this.hrStove = hrStove;
    }

    public int getEnvironment() {
        return environment;
    }

    public void setEnvironment(int environment) {
        this.environment = environment;
    }

    public int getFridge() {
        return fridge;
    }

    public void setFridge(int fridge) {
        this.fridge = fridge;
    }

    public int getPhotolysis() {
        return photolysis;
    }

    public void setPhotolysis(int photolysis) {
        this.photolysis = photolysis;
    }
}


