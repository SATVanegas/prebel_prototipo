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

}