package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.TestsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "colors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Color{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    protected String unit;
    protected String specification;
    protected String method;
    protected String initialResultsDevelopmentLaboratory;
    protected String initialResultsStabilityLaboratory;
    protected int time;
    protected int equipment;

    public Color(TestsDTO dto) {
        this.unit = dto.getUnit();
        this.specification = dto.getSpecification();
        this.method = dto.getMethod();
        this.initialResultsDevelopmentLaboratory = dto.getInitialResultsDevelopmentLaboratory();
        this.initialResultsStabilityLaboratory = dto.getInitialResultsStabilityLaboratory();
        this.time = dto.getTime();
        this.equipment = dto.getEquipment();
    }
}
