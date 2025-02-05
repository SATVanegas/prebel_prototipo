package com.prebel.prototipo.webapp.models.weekly_planner;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "weekly_calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechnicianSchedule> TechniciansSchedules;

}
