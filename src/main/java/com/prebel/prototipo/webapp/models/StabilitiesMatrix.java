package com.prebel.prototipo.webapp.models;

import java.util.Date;
import java.util.List;

import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stabilities_matrix")
@NoArgsConstructor
@AllArgsConstructor
public class StabilitiesMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectCode;
    private String formulaCode;
    private String ptReference;
    private String bulkReference;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String studyDescription;
    private Integer locationEnvironment;
    private Integer locationOven;
    private Integer locationFridge;
    private Integer locationPhotolysisChamber;
    private String batch;
    private String container;
    private String dosage;
    private String packagingMaterial;
    private String containerColor;
    private String coverMaterial;
    private String coverColor;
    private String supplier;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    private String category;
    private String cosmeticForm;
    private String studyJustification;
    private String studyType;
    private Date deliveryDatePt;
    private Date dateStartFormat;
    private Date startDate;
    private Integer startMonth;
    private Integer startYear;
    private Date endDate;
    private Integer endMonth;
    private Integer endYear;

    @OneToMany(mappedBy = "stabilitiesMatrix", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private List<Inspection> inspections;

    private String qualification;
    private Integer validity;
    private String justificationRating;

    @ManyToOne
    @JoinColumn(name = "chemical_id", nullable = false)
    private User chemical;

    @ManyToOne
    @JoinColumn(name = "engineer_id", nullable = false)
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
        return ptReference;
    }

    public void setPtReference(String ptReference) {
        this.ptReference = ptReference;
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
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

    public Integer getLocationEnvironment() {
        return locationEnvironment;
    }

    public void setLocationEnvironment(Integer locationEnvironment) {
        this.locationEnvironment = locationEnvironment;
    }

    public Integer getLocationOven() {
        return locationOven;
    }

    public void setLocationOven(Integer locationOven) {
        this.locationOven = locationOven;
    }

    public Integer getLocationFridge() {
        return locationFridge;
    }

    public void setLocationFridge(Integer locationFridge) {
        this.locationFridge = locationFridge;
    }

    public Integer getLocationPhotolysisChamber() {
        return locationPhotolysisChamber;
    }

    public void setLocationPhotolysisChamber(Integer locationPhotolysisChamber) {
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

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
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

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
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

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
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
