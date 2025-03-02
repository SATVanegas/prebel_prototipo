package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.updates.TechnicianScheduleUpdateDTO;
import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.services.role_module_services.RoleService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import com.prebel.prototipo.webapp.services.utils.DateService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Transactional
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
        List<TechnicianSchedule> results = new ArrayList<>();
        technicianScheduleRepository.findAll().forEach(results::add);
        return results;
    }

    public List<TechnicianSchedule> getTechnicianSchedulesByTechnicianId(Long technicianId) {
        User technician = userService.getUserById(technicianId)
                .orElseThrow(() -> new RuntimeException("Técnico no encontrado con ID: " + technicianId));
        return technicianScheduleRepository.findByTechnician(technician);
    }

    @Transactional
    public boolean deleteTechnicianSchedule(Long id) {
        if (technicianScheduleRepository.existsById(id)) {
            technicianScheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void updateTechnicianSchedule(Long id, TechnicianScheduleUpdateDTO dto) {
        TechnicianSchedule schedule = technicianScheduleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TechnicianSchedule no encontrado con ID: " + id));

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

        if (dto.getSchedule() != null) {
            schedule.setSchedule(dto.getSchedule()); // Actualizar solo el horario
        }

        if (dto.getInfo() != null) {
            schedule.setInfo(dto.getInfo()); // Actualizar solo la descripción
        }

        if (schedule.getId() != id) {
            throw new IllegalStateException("Error: El ID de la programación no coincide con el solicitado");
        }

        technicianScheduleRepository.save(schedule);
    }
}