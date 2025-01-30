package com.prebel.prototipo.webapp.models;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "stabilities_matrix")
public class StabilitiesMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectCode;
    private String formulaCode;
    private String PtReference;
    private String bulkReference;

    @ManyToOne
    @JoinColumn(name = "product_id")
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

    @ManyToOne
    @JoinColumn(name = "customer_id")
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

    @OneToMany(mappedBy = "stabilitiesMatrix", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inspection> inspections;

    private String qualification;
    private int validity;
    private String justificationRating;

    @ManyToOne
    @JoinColumn(name = "chemical_id")
    private User chemical;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private User engineer;

    public StabilitiesMatrix() {
    }

    public StabilitiesMatrix(String projectCode, String formulaCode, String ptReference, String bulkReference, Product product, String studyDescription, int locationEnvironment, int locationOven, int locationFridge, int locationPhotolysisChamber, String batch, String container, String dosage, String packagingMaterial, String containerColor, String coverMaterial, String coverColor, String supplier, User customer, String category, String cosmeticForm, String studyJustification, String studyTipe, Date deliveryDatePt, Date dateStartFormat, Date startDate, int startMonth, int startYear, Date endDate, int endMonth, int endYear, List<Inspection> inspections, String qualification, int validity, String justificationRating, User chemical, User engineer) {
        this.projectCode = projectCode;
        this.formulaCode = formulaCode;
        this.PtReference = ptReference;
        this.bulkReference = bulkReference;
        this.product = product;
        this.StudyDescription = studyDescription;
        this.locationEnvironment = locationEnvironment;
        this.locationOven = locationOven;
        this.locationFridge = locationFridge;
        this.locationPhotolysisChamber = locationPhotolysisChamber;
        this.batch = batch;
        this.container = container;
        this.dosage = dosage;
        this.packagingMaterial = packagingMaterial;
        this.containerColor = containerColor;
        this.coverMaterial = coverMaterial;
        this.coverColor = coverColor;
        this.supplier = supplier;
        this.customer = customer;
        this.category = category;
        this.cosmeticForm = cosmeticForm;
        this.studyJustification = studyJustification;
        this.studyTipe = studyTipe;
        this.deliveryDatePt = deliveryDatePt;
        this.dateStartFormat = dateStartFormat;
        this.startDate = startDate;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endDate = endDate;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.inspections = inspections;
        this.qualification = qualification;
        this.validity = validity;
        this.justificationRating = justificationRating;
        this.chemical = chemical;
        this.engineer = engineer;
    }

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
