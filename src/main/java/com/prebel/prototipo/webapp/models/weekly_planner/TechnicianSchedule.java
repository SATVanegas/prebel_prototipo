package com.prebel.prototipo.webapp.models.weekly_planner;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import java.util.Date;


@Entity
@Table(name = "tecnician_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"technician", "assignedRole", "weekly_calendar"})
public class TechnicianSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Enumerated(EnumType.STRING)
    private DayWeek day;

    @ManyToOne
    @JoinColumn(name = "technician_id",nullable = false)
    @JsonIgnoreProperties({"role", "password", "resetCode", "email", "weeklyTasks"})
    private User technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnoreProperties({"roleModules"})
    private Role assignedRole;

    private String schedule;
    private String info;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weekly_calendar_id")
    @JsonBackReference
    private WeeklyCalendar weekly_calendar;

    public TechnicianSchedule(@Valid TechnicianScheduleDTO dto, User technician, Role assignedRole, WeeklyCalendar weeklyCalendar, DayWeek dayWeek) {
        this.date = dto.getDate();
        this.day = dayWeek;
        this.technician = technician;
        this.assignedRole = assignedRole;
        this.schedule = dto.getSchedule();
        this.info = dto.getInfo();
        this.weekly_calendar = weeklyCalendar;
    }
}
