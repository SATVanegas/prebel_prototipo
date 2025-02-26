package com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request;

import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
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
public class TechnicianScheduleDTO {

    @NotNull(message = "La fecha (date) es obligatoria")
    private Date date;

    @NotNull(message = "El día de la semana (day) es obligatorio")
    private DayWeek day;

    @NotNull(message = "El ID del técnico (technicianId) es obligatorio")
    private Long technicianId;

    @NotNull(message = "El ID del rol asignado (assignedRoleId) es obligatorio")
    private Long assignedRoleId;

    private String schedule;
    private String info;

    @NotNull(message = "El ID del calendario semanal (weeklyCalendarId) es obligatorio")
    private Long weeklyCalendarId;
}
