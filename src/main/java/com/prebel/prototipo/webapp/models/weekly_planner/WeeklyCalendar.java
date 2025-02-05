package com.prebel.prototipo.webapp.models.weekly_planner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.role_module.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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

    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = true)
    private Role assignedRole;
}
