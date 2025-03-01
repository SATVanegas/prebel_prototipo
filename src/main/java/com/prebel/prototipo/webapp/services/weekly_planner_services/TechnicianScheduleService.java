package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicianScheduleService {

    private final TechnicianScheduleRepository technicianScheduleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WeeklyCalendarRepository weeklyCalendarRepository;

    public TechnicianScheduleService(
            TechnicianScheduleRepository technicianScheduleRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            WeeklyCalendarRepository weeklyCalendarRepository) {
        this.technicianScheduleRepository = technicianScheduleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.weeklyCalendarRepository = weeklyCalendarRepository;
    }

    public void createTechnicianSchedule(@Valid TechnicianScheduleDTO dto) {
        // Obtener el técnico (usuario)
        User technician = userRepository.findById(dto.getTechnicianId())
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado con ID: " + dto.getTechnicianId()));

        // Obtener el rol asignado
        Role assignedRole = roleRepository.findById(dto.getAssignedRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + dto.getAssignedRoleId()));

        // Obtener el calendario semanal
        WeeklyCalendar weeklyCalendar = weeklyCalendarRepository.findById(dto.getWeeklyCalendarId())
                .orElseThrow(() -> new RuntimeException("Calendario semanal no encontrado con ID: " + dto.getWeeklyCalendarId()));

        // Crear y guardar el nuevo horario de técnico
        TechnicianSchedule technicianSchedule = new TechnicianSchedule();
        technicianSchedule.setDate(dto.getDate());
        technicianSchedule.setDay(dto.getDay());
        technicianSchedule.setTechnician(technician);
        technicianSchedule.setAssignedRole(assignedRole);
        technicianSchedule.setSchedule(dto.getSchedule());
        technicianSchedule.setInfo(dto.getInfo());
        technicianSchedule.setWeekly_calendar(weeklyCalendar);

        technicianScheduleRepository.save(technicianSchedule);
    }

    public Optional<TechnicianSchedule> getTechnicianSchedule(Long id) {
        return technicianScheduleRepository.findById(id);
    }

    public List<TechnicianSchedule> getAllTechnicianSchedules() {
        List<TechnicianSchedule> schedules = new ArrayList<>();
        technicianScheduleRepository.findAll().forEach(schedules::add);
        return schedules;
    }
}