package com.prebel.prototipo.webapp.models.laboratory_reports;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inspections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date expectedDate;
    private Date realDate;
    private int responseTime;
    private int aerosolStove;
    private int inOut;
    private int stove;
    private int hrStove;
    private int environment;
    private int fridge;
    private int photolysis;

    @ManyToOne
    @JoinColumn(name = "stabilities_matrix_id")
    @JsonIgnore
    private StabilitiesMatrix stabilitiesMatrix;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonIgnore
    private Test test;

    public Inspection(@Valid InspectionDTO dto, StabilitiesMatrix stabilitiesMatrix, Test test) {
        this.expectedDate = dto.getExpectedDate();
        this.realDate = dto.getRealDate();
        this.responseTime = dto.getResponseTime();
        this.aerosolStove = dto.getAerosolStove();
        this.inOut = dto.getInOut();
        this.stove = dto.getStove();
        this.hrStove = dto.getHrStove();
        this.environment = dto.getEnvironment();
        this.fridge = dto.getFridge();
        this.photolysis = dto.getPhotolysis();
        this.stabilitiesMatrix = stabilitiesMatrix;
        this.test = test;
    }

    public Inspection(@Valid InspectionDTO dto, StabilitiesMatrixDTO stabilitiesMatrix, Test test) {
    }
}


