package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "temperatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String unit;
    private int time;
    private int equipment;

    @OneToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    public Temperature(TestTemperatureDTO dto, Test test) {
        this.unit = dto.getUnit();
        this.time = dto.getTime();
        this.equipment = dto.getEquipment();
        this.test = test;
    }
}
