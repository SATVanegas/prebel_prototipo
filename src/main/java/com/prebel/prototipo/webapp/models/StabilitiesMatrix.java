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

}
