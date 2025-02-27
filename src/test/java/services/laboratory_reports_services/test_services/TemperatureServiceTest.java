package services.laboratory_reports_services.test_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TemperatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceTest {

    @Mock
    private TemperatureRepository temperatureRepository;

    @InjectMocks
    private TemperatureService temperatureService;

    private TestTemperatureDTO testTemperatureDTO;
    private Temperature temperature;

    @BeforeEach
    void setUp() {
        testTemperatureDTO = new TestTemperatureDTO("unit",1,1);
        temperature = new Temperature(testTemperatureDTO);
    }

    @Test
    void testGetTemperatureById_Found() {
        when(temperatureRepository.findById(1L)).thenReturn(Optional.of(temperature));

        Optional<Temperature> result = temperatureService.getTemperatureById(1L);

        assertTrue(result.isPresent());
        assertEquals(temperature, result.get());
        verify(temperatureRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTemperature() {
        temperatureService.createTemperature(testTemperatureDTO);

        verify(temperatureRepository, times(1)).save(any(Temperature.class));
    }
}
