package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestStorageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "storages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int maxTemperature;
    private int minTemperature;
    private String equipmentCode;
    private String description;

    @OneToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    public Storage(TestStorageDTO dto, Test test) {
        this.maxTemperature = dto.getMaxTemperature();
        this.minTemperature = dto.getMinTemperature();
        this.equipmentCode = dto.getEquipmentCode();
        this.description = dto.getDescription();
        this.test = test;
    }
}
