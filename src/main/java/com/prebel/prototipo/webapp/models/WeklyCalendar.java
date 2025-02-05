package com.prebel.prototipo.webapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "wekly_calendar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeklyCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;
    private Date finishDate;


}
