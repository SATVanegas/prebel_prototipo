package com.prebel.prototipo.webapp.models.weekly_planner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayWeek {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado");

    private final String nombre;

    @Override
    public String toString() {
        return nombre;
    }

    public static DayWeek fromNombre(String nombre) {
        for (DayWeek day : DayWeek.values()) {
            if (day.getNombre().equalsIgnoreCase(nombre)) {
                return day;
            }
        }
        throw new IllegalArgumentException("Día inválido: " + nombre);
    }
}