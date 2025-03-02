package com.prebel.prototipo.webapp.models.laboratory_reports;

import java.util.Date;
import java.util.List;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productDescription;
    private String reference;
    private String batch;
    private String packagingType;
    private String packagingMaterial;
    private String containerColor;
    private String lidMaterial;
    private String lidColor;
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

    public Product(@Valid ProductDTO dto, User customer, User responsibleChemist,
                   User responsibleEngineer, User responsibleAnalyst, User technicianInCharge) {
        this.productDescription = dto.getProductDescription();
        this.reference = dto.getReference();
        this.batch = dto.getBatch();
        this.packagingType = dto.getPackagingType();
        this.packagingMaterial = dto.getPackagingMaterial();
        this.containerColor = dto.getContainerColor();
        this.lidMaterial = dto.getLidMaterial();
        this.lidColor = dto.getLidColor();
        this.formulaNumber = dto.getFormulaNumber();
        this.projectCode = dto.getProjectCode();
        this.projectName = dto.getProjectName();
        this.customer = customer;
        this.brand = dto.getBrand();
        this.studyType = dto.getStudyType();
        this.consecutive = dto.getConsecutive();
        this.justification = dto.getJustification();
        this.qualification = dto.getQualification();
        this.establishedValidity = dto.getEstablishedValidity();
        this.responsibleChemist = responsibleChemist;
        this.responsibleEngineer = responsibleEngineer;
        this.responsibleAnalyst = responsibleAnalyst;
        this.technicianInCharge = technicianInCharge;
        this.studyDuration = dto.getStudyDuration();
        this.startDate = dto.getStartDate();
        this.finishDate = dto.getFinishDate();
    }
}
