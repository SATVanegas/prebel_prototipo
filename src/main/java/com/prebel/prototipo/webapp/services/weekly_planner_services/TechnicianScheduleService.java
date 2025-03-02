package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.request.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.dtos.updates.weekly_planner_updates.TechnicianScheduleUpdateDTO;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.services.role_module_services.RoleService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import com.prebel.prototipo.webapp.services.utils.DateService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<TechnicianSchedule> getAllTechnicianScheduleInWeeklyCalendar(Long weeklyCalendarId) {
        List<TechnicianSchedule> allSchedules = (List<TechnicianSchedule>) technicianScheduleRepository.findAll(); // Obtener todos los registros

        return allSchedules.stream()
                .filter(schedule -> schedule.getWeeklyCalendar() != null &&
                        schedule.getWeeklyCalendar().getId().equals(weeklyCalendarId)) // Filtrar por ID
                .collect(Collectors.toList());
    }

    public void updateTechnicianSchedule(Long id, TechnicianScheduleUpdateDTO dto) {
        TechnicianSchedule schedule = technicianScheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Technician Schedule no encontrado con ID: " + id));

        // Actualizar solo los campos que vienen en el DTO
        if (dto.getTechnicianId() != null) {
            User technician = userService.getUserById(dto.getTechnicianId())
                    .orElseThrow(() -> new NoSuchElementException("Técnico no encontrado con ID: " + dto.getTechnicianId()));
            schedule.setTechnician(technician);
        }

        if (dto.getAssignedRoleId() != null) {
            Role assignedRole = roleService.getRoleById(dto.getAssignedRoleId())
                    .orElseThrow(() -> new NoSuchElementException("Rol no encontrado con ID: " + dto.getAssignedRoleId()));
            schedule.setAssignedRole(assignedRole);
        }

        if (dto.getDay() != null) {
            DayWeek dayWeek = dateService.getDayFromString(dto.getDay())
                    .orElseThrow(() -> new NoSuchElementException("El día de la semana se ingresó incorrectamente"));
            schedule.setDay(dayWeek);
        }

        technicianScheduleRepository.save(schedule);
    }

    public void deleteTechnicianSchedule(Long id) {
        if (!technicianScheduleRepository.existsById(id)) {
            throw new NoSuchElementException("Technician Schedule no encontrado con ID: " + id);
        }
        technicianScheduleRepository.deleteById(id);
    }


}