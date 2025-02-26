package com.prebel.prototipo.webapp.models.laboratory_reports;

import java.util.Date;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import jakarta.persistence.*;
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
    private StabilitiesMatrix stabilitiesMatrix;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

}


