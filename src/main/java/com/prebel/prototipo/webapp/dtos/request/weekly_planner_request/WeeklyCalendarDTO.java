package com.prebel.prototipo.webapp.dtos.request.weekly_planner_request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyCalendarDTO {

    @NotNull(message = "La fecha de inicio (startDate) es obligatoria")
    private LocalDateTime startDate;

    @NotNull(message = "La fecha de finalización (endDate) es obligatoria")
    private LocalDateTime endDate;
}
