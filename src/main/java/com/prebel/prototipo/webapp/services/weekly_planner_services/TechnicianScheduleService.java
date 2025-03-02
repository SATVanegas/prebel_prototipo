package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import com.prebel.prototipo.webapp.services.role_module_services.RoleService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import com.prebel.prototipo.webapp.services.utils.DateService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicianScheduleService {

    private final TechnicianScheduleRepository technicianScheduleRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final WeeklyCalendarService weeklyCalendarService;
    private final DateService dateService = new DateService();

    public TechnicianScheduleService(
            TechnicianScheduleRepository technicianScheduleRepository, UserService userService, RoleService roleService, WeeklyCalendarService weeklyCalendarService) {
        this.technicianScheduleRepository = technicianScheduleRepository;
        this.userService = userService;
        this.roleService = roleService;
        this.weeklyCalendarService = weeklyCalendarService;
    }

    public void createTechnicianSchedule(@Valid TechnicianScheduleDTO dto) {
        User technician = userService.getUserById(dto.getTechnicianId())
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado con ID: " + dto.getTechnicianId()));

        Role assignedRole = roleService.getRoleById(dto.getAssignedRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + dto.getAssignedRoleId()));

        WeeklyCalendar weeklyCalendar = weeklyCalendarService.getWeeklyCalendarById(dto.getWeeklyCalendarId())
                .orElseThrow(() -> new RuntimeException("Calendario semanal no encontrado con ID: " + dto.getWeeklyCalendarId()));

        DayWeek dayWeek=dateService.getDayFromString(dto.getDay())
                .orElseThrow(() -> new RuntimeException("El dia de la semana se ingreso incorrectamente"));

        TechnicianSchedule technicianSchedule = new TechnicianSchedule(dto,technician,assignedRole,weeklyCalendar,dayWeek);
        technicianScheduleRepository.save(technicianSchedule);
    }

    public Optional<TechnicianSchedule> getTechnicianSchedule(Long id) {
        return technicianScheduleRepository.findById(id);
    }

    public List<TechnicianSchedule> getAllTechnicianSchedules() {
        return (List<TechnicianSchedule>) technicianScheduleRepository.findAll();
    }

}