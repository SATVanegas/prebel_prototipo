package com.prebel.prototipo.webapp.models.laboratory_reports;

import java.util.Date;
import java.util.List;

import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stabilities_matrix")
@Getter
@Setter
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

}
