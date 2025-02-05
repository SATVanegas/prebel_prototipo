package com.prebel.prototipo.webapp.models.weekly_planner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "weekly_calendar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date StartDate;
    private Date FinishDate;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> MondayTechniciansSchedule;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> TuesdayTechniciansSchedule;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> WednesdayTechniciansSchedule;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> ThursdayTechniciansSchedule;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> FridayTechniciansSchedule;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> SaturdayTechniciansSchedule;

}
