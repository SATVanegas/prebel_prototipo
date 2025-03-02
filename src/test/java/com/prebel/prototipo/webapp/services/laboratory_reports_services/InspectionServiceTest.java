package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.InspectionRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.InspectionService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.StabilitiesMatrixService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InspectionServiceTest {

    @Mock
    private InspectionRepository inspectionRepository;

    @Mock
    private StabilitiesMatrixService stabilitiesMatrixService;

    @Mock
    private TestService testService;

    @InjectMocks
    private InspectionService inspectionService;

    private InspectionDTO inspectionDTO;
    private StabilitiesMatrix stabilitiesMatrix;
    private com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test;
    private Inspection inspection;

    @BeforeEach
    void setUp() {
        inspectionDTO = new InspectionDTO();
        inspectionDTO.setStabilitiesMatrixId(1L);
        inspectionDTO.setTestId(1L);

        stabilitiesMatrix = new StabilitiesMatrix();
        stabilitiesMatrix.setId(1L);

        test = new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test();
        test.setId(1L);

        inspection = new Inspection(inspectionDTO, stabilitiesMatrix, test);
    }

    @Test
    void getInspectionById_Success() {
        when(inspectionRepository.findById(1L)).thenReturn(Optional.of(inspection));

        Optional<Inspection> result = inspectionService.getInspectionById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getStabilitiesMatrix().getId());
        assertEquals(1L, result.get().getTest().getId());

        verify(inspectionRepository, times(1)).findById(1L);
    }

    @Test
    void getInspectionById_NotFound() {
        when(inspectionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Inspection> result = inspectionService.getInspectionById(1L);

        assertFalse(result.isPresent());

        verify(inspectionRepository, times(1)).findById(1L);
    }

    @Test
    void createInspection_Success() {
        when(stabilitiesMatrixService.getStabilitiesMatrixById(1L)).thenReturn(Optional.of(stabilitiesMatrix));
        when(testService.getTestById(1L)).thenReturn(Optional.of(test));
        when(inspectionRepository.save(any(Inspection.class))).thenReturn(inspection);

        inspectionService.createInspection(inspectionDTO);

        verify(stabilitiesMatrixService, times(1)).getStabilitiesMatrixById(1L);
        verify(testService, times(1)).getTestById(1L);
        verify(inspectionRepository, times(1)).save(any(Inspection.class));
    }

    @Test
    void createInspection_StabilitiesMatrixNotFound() {
        when(stabilitiesMatrixService.getStabilitiesMatrixById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            inspectionService.createInspection(inspectionDTO);
        });

        assertEquals("La matriz de estabilidad con ID 1 no existe", exception.getMessage());

        verify(stabilitiesMatrixService, times(1)).getStabilitiesMatrixById(1L);
        verify(testService, never()).getTestById(anyLong());
        verify(inspectionRepository, never()).save(any(Inspection.class));
    }

    @Test
    void createInspection_TestNotFound() {
        when(stabilitiesMatrixService.getStabilitiesMatrixById(1L)).thenReturn(Optional.of(stabilitiesMatrix));
        when(testService.getTestById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            inspectionService.createInspection(inspectionDTO);
        });

        assertEquals("El test con ID 1 no existe", exception.getMessage());

        verify(stabilitiesMatrixService, times(1)).getStabilitiesMatrixById(1L);
        verify(testService, times(1)).getTestById(1L);
        verify(inspectionRepository, never()).save(any(Inspection.class));
    }
}