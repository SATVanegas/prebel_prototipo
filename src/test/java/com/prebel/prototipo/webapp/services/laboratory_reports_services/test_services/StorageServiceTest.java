package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests.test_request.TestStorageDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.StorageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private StorageService storageService;

    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        storage.setId(1L);
    }

    @Test
    void testGetStorageById_Found() {
        when(storageRepository.findById(1L)).thenReturn(Optional.of(storage));

        Optional<Storage> result = storageService.getStorageById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(storageRepository).findById(1L);
    }

    @Test
    void testCreateStorage() {
        TestStorageDTO dto = new TestStorageDTO(1,1,"code","description");
        storageService.createStorage(dto);
        verify(storageRepository).save(any(Storage.class));
    }
}
