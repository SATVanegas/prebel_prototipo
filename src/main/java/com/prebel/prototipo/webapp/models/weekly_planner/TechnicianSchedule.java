package com.prebel.prototipo.webapp.models.weekly_planner;

import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Entity
@Table(name = "tecnician_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Enumerated(EnumType.STRING)
    private DayWeek day;

    @ManyToOne
    @JoinColumn(name = "technician_id",nullable = false)
    private User technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role assignedRole;

    private String schedule;
    private String info;

    @ManyToOne
    @JoinColumn(name = "weekly_calendar_id")
    private WeeklyCalendar weekly_calendar;

    public void setDayFromString(String dayName) {
        for (DayWeek d : DayWeek.values()) {
            if (d.toString().equalsIgnoreCase(dayName)) {
                this.day = d;
                return;
            }
        }
        throw new IllegalArgumentException("Nombre de día inválido: " + dayName);
    }
}
