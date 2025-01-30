package com.prebel.prototipo.webapp.models;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    private String brand;
    private String studyType;
    private int consecutive;
    private String justification;
    private String qualification;
    private String establishedValidity;

    @ManyToOne
    @JoinColumn(name = "responsible_chemist_id")
    private User responsibleChemist;

    @ManyToOne
    @JoinColumn(name = "responsible_engineer_id")
    private User responsibleEngineer;

    @ManyToOne
    @JoinColumn(name = "responsible_analyst_id")
    private User responsibleAnalyst;

    @ManyToOne
    @JoinColumn(name = "technician_in_charge_id")
    private User technicianInCharge;

    private int studyDuration; //In months
    private Date startDate;
    private Date finishDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Test> tests;

    public Product(String productDescription, String reference, String batch, String packagingType, String packagingMaterial, String containerColor, String lidMaterial, String lidColor, String formulaNumber, String projectCode, String projectName, User customer, String brand, String studyType, int consecutive, String justification, String qualification, String establishedValidity, User responsibleChemist, User responsibleEngineer, User responsibleAnalyst, User technicianInCharge, int studyDuration, Date startDate, Date finishDate, List<Test> test) {
        this.productDescription = productDescription;
        this.reference = reference;
        this.batch = batch;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.containerColor = containerColor;
        this.lidMaterial = lidMaterial;
        this.lidColor = lidColor;
        this.formulaNumber = formulaNumber;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.customer = customer;
        this.brand = brand;
        this.studyType = studyType;
        this.consecutive = consecutive;
        this.justification = justification;
        this.qualification = qualification;
        this.establishedValidity = establishedValidity;
        this.responsibleChemist = responsibleChemist;
        this.responsibleEngineer = responsibleEngineer;
        this.responsibleAnalyst = responsibleAnalyst;
        this.technicianInCharge = technicianInCharge;
        this.studyDuration = studyDuration;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.tests = test;
    }

    public Product() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
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

    public String getLidMaterial() {
        return lidMaterial;
    }

    public void setLidMaterial(String lidMaterial) {
        this.lidMaterial = lidMaterial;
    }

    public String getLidColor() {
        return lidColor;
    }

    public void setLidColor(String lidColor) {
        this.lidColor = lidColor;
    }

    public String getFormulaNumber() {
        return formulaNumber;
    }

    public void setFormulaNumber(String formulaNumber) {
        this.formulaNumber = formulaNumber;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public int getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(int consecutive) {
        this.consecutive = consecutive;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEstablishedValidity() {
        return establishedValidity;
    }

    public void setEstablishedValidity(String establishedValidity) {
        this.establishedValidity = establishedValidity;
    }

    public User getResponsibleChemist() {
        return responsibleChemist;
    }

    public void setResponsibleChemist(User responsibleChemist) {
        this.responsibleChemist = responsibleChemist;
    }

    public User getResponsibleEngineer() {
        return responsibleEngineer;
    }

    public void setResponsibleEngineer(User responsibleEngineer) {
        this.responsibleEngineer = responsibleEngineer;
    }

    public User getResponsibleAnalyst() {
        return responsibleAnalyst;
    }

    public void setResponsibleAnalyst(User responsibleAnalyst) {
        this.responsibleAnalyst = responsibleAnalyst;
    }

    public User getTechnicianInCharge() {
        return technicianInCharge;
    }

    public void setTechnicianInCharge(User technicianInCharge) {
        this.technicianInCharge = technicianInCharge;
    }

    public int getStudyDuration() {
        return studyDuration;
    }

    public void setStudyDuration(int studyDuration) {
        this.studyDuration = studyDuration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<Test> getTest() {
        return tests;
    }

    public void setTest(List<Test> test) {
        this.tests = test;
    }
}
