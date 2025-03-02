package com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TechnicianScheduleUpdateDTO {
    private Long technicianId;
    private Long assignedRoleId;
    private String schedule;
    private String info;
}
