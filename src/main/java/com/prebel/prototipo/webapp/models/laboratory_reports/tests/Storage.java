package com.prebel.prototipo.webapp.models.laboratory_reports.tests;

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

    private int maxTemperature; //In Celcius
    private int minTemperature; //In Celcius
    private String equipmentCode;
    private String description;

}
