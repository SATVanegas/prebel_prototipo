package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.TestConditionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.Test;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "conditions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private EnumTest type;

    private String unit;
    private String specification;
    private String method;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;
    private int time;
    private int equipment;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    public Condition(@Valid TestConditionDTO dto, Test test) {
        this.type = dto.getType();
        this.unit = dto.getUnit();
        this.specification = dto.getSpecification();
        this.method = dto.getMethod();
        this.initialResultsDevelopmentLaboratory = dto.getInitialResultsDevelopmentLaboratory();
        this.initialResultsStabilityLaboratory = dto.getInitialResultsStabilityLaboratory();
        this.time = dto.getTime();
        this.equipment = dto.getEquipment();
        this.test = test;
    }

}
