package com.prebel.prototipo.webapp.models;

import java.util.Date;

public class Product {

    private String productDescription;
    private String reference;
    private String batch;
    private String packagingType; // to define if it remains as a java enum
    private String packagingMaterial; // to define if it remains as a java enum
    private String containerColor; // to define if it remains as a java enum
    private String lidMaterial; // to define if it remains as a java enum
    private String lidColor; // to define if it remains as a java enum
    private String formulaNumber;
    private String projectCode;
    private String projectName;
    private Person customer;
    private String brand;
    private String studyType;
    private int consecutive;
    private String justification;
    private String qualification;
    private String establishedValidity;
    private Person responsibleChemist;
    private Person responsibleEngineer;
    private Person responsibleAnalyst;
    private Person technicianInCharge;
    private int studyDuration; //In months
    private Date startDate;
    private Date finishDate;
    private Test[] test;
}
