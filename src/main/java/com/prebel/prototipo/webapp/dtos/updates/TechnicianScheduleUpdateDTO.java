package com.prebel.prototipo.webapp.dtos.updates;

import jakarta.validation.constraints.NotNull;
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
    private Long technicianId;
    private Long assignedRoleId;
    private String schedule;
    private String info;
}
