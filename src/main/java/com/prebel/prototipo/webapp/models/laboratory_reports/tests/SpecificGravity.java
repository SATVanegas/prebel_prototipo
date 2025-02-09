package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.TestsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "specific_gravities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecificGravity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String unit;
    private String specification;
    private String method;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;
    private int time;
    private int equipment;

    public SpecificGravity(TestsDTO dto) {
        this.unit = dto.getUnit();
        this.specification = dto.getSpecification();
        this.method = dto.getMethod();
        this.initialResultsDevelopmentLaboratory = dto.getInitialResultsDevelopmentLaboratory();
        this.initialResultsStabilityLaboratory = dto.getInitialResultsStabilityLaboratory();
        this.time = dto.getTime();
        this.equipment = dto.getEquipment();
    }
}
