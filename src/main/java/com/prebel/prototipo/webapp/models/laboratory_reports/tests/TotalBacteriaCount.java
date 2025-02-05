package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "total_bacteria_counts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalBacteriaCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String unit;
    private String specification;
    private String method;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;
    private int time; // In weeks
    private int equipment;

}
