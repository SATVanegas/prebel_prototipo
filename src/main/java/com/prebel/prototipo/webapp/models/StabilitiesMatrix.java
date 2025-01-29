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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getFormulaCode() {
        return formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getPtReference() {
        return PtReference;
    }

    public void setPtReference(String ptReference) {
        PtReference = ptReference;
    }

    public String getBulkReference() {
        return bulkReference;
    }

    public void setBulkReference(String bulkReference) {
        this.bulkReference = bulkReference;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStudyDescription() {
        return StudyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        StudyDescription = studyDescription;
    }

    public int getLocationEnvironment() {
        return locationEnvironment;
    }

    public void setLocationEnvironment(int locationEnvironment) {
        this.locationEnvironment = locationEnvironment;
    }

    public int getLocationOven() {
        return locationOven;
    }

    public void setLocationOven(int locationOven) {
        this.locationOven = locationOven;
    }

    public int getLocationFridge() {
        return locationFridge;
    }

    public void setLocationFridge(int locationFridge) {
        this.locationFridge = locationFridge;
    }

    public int getLocationPhotolysisChamber() {
        return locationPhotolysisChamber;
    }

    public void setLocationPhotolysisChamber(int locationPhotolysisChamber) {
        this.locationPhotolysisChamber = locationPhotolysisChamber;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public String getContainerColor() {
        return containerColor;
    }

    public void setContainerColor(String containerColor) {
        this.containerColor = containerColor;
    }

    public String getCoverMaterial() {
        return coverMaterial;
    }

    public void setCoverMaterial(String coverMaterial) {
        this.coverMaterial = coverMaterial;
    }

    public String getCoverColor() {
        return coverColor;
    }

    public void setCoverColor(String coverColor) {
        this.coverColor = coverColor;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCosmeticForm() {
        return cosmeticForm;
    }

    public void setCosmeticForm(String cosmeticForm) {
        this.cosmeticForm = cosmeticForm;
    }

    public String getStudyJustification() {
        return studyJustification;
    }

    public void setStudyJustification(String studyJustification) {
        this.studyJustification = studyJustification;
    }

    public String getStudyTipe() {
        return studyTipe;
    }

    public void setStudyTipe(String studyTipe) {
        this.studyTipe = studyTipe;
    }

    public Date getDeliveryDatePt() {
        return deliveryDatePt;
    }

    public void setDeliveryDatePt(Date deliveryDatePt) {
        this.deliveryDatePt = deliveryDatePt;
    }

    public Date getDateStartFormat() {
        return dateStartFormat;
    }

    public void setDateStartFormat(Date dateStartFormat) {
        this.dateStartFormat = dateStartFormat;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public List<Inspection> getInspections() {
        return inspections;
    }

    public void setInspections(List<Inspection> inspections) {
        this.inspections = inspections;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getJustificationRating() {
        return justificationRating;
    }

    public void setJustificationRating(String justificationRating) {
        this.justificationRating = justificationRating;
    }

    public User getChemical() {
        return chemical;
    }

    public void setChemical(User chemical) {
        this.chemical = chemical;
    }

    public User getEngineer() {
        return engineer;
    }

    public void setEngineer(User engineer) {
        this.engineer = engineer;
    }
}
