package com.prebel.prototipo.webapp.dtos.updates.weekly_planner_updates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicianScheduleUpdateDTO {
    private Date date;

    private String day;

    private Long technicianId;

    private Long assignedRoleId;

    private String schedule;
    private String info;
}

