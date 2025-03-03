package services.laboratory_reports_services.test_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestConditionDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ConditionRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.ConditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ConditionServiceTest {

    @Mock
    private ConditionRepository conditionRepository;

    @InjectMocks
    private ConditionService conditionService;

    private TestConditionDTO testConditionDTO;
    private Condition condition;
    private TestDTO testDTO;

    @BeforeEach
    void setUp() {
        testConditionDTO = new TestConditionDTO();

        condition = new Condition();
        condition.setId(1L);
        condition.setType(EnumTest.COLOR);

        testDTO = new TestDTO();
    }

    @Test
    void testGetConditionById_Found() {
        when(conditionRepository.findById(1L)).thenReturn(Optional.of(condition));

        Optional<Condition> result = conditionService.getConditionById(1L);

        assertTrue(result.isPresent());
        assertEquals(condition, result.get());
        verify(conditionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetConditionById_NotFound() {
        when(conditionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Condition> result = conditionService.getConditionById(1L);

        assertFalse(result.isPresent());
        verify(conditionRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCondition() {
        conditionService.createCondition(testConditionDTO);

        verify(conditionRepository, times(1)).save(any(Condition.class));
    }

}
