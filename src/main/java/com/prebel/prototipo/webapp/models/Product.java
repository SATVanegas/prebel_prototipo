package com.prebel.prototipo.webapp.models;

import java.util.Date;
import java.util.List;

public class Product {

    private Long id;
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
    private User customer;
    private String brand;
    private String studyType;
    private int consecutive;
    private String justification;
    private String qualification;
    private String establishedValidity;
    private User responsibleChemist;
    private User responsibleEngineer;
    private User responsibleAnalyst;
    private User technicianInCharge;
    private int studyDuration; //In months
    private Date startDate;
    private Date finishDate;
    private List<Test> test;
}
