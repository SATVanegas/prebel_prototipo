package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

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
    private int time; // In weeks
    private int equipment;

}
