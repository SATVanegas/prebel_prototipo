package com.prebel.prototipo.webapp.models.weekly_planner;

import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tecnicians_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechniciansSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayWeek day;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private User technician;

    private String schedule;
    private String info;
}
