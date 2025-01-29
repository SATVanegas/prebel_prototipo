package com.prebel.prototipo.webapp.models;

import java.util.Date;
import java.util.List;

public class StabilitiesMatrix {

    private Long id;
    private String projectCode;
    private String formulaCode;
    private String PtReference;
    private String bulkReference;
    private Product product;
    private String StudyDescription;
    private int locationEnvironment;
    private int locationOven;
    private int locationFridge;
    private int locationPhotolysisChamber;
    private String batch;
    private String container;
    private String dosage;
    private String packagingMaterial;
    private String containerColor;
    private String coverMaterial;
    private String coverColor;
    private String supplier;
    private User customer;
    private String category;
    private String cosmeticForm;
    private String studyJustification;
    private String studyTipe;
    private Date deliveryDatePt;
    private Date dateStartFormat;
    private Date startDate;
    private int startMonth;
    private int startYear;
    private Date endDate;
    private int endMonth;
    private int endYear;
    private List<Inspection> inspections;
    private String qualification;
    private int validity;
    private String justificationRating;
    private User chemical;
    private User engineer;


}
