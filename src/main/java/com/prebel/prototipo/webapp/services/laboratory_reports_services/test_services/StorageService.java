package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestStorageDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.StorageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StorageService {

    private final StorageRepository storageRepository;
    private final TestService testService;

    public StorageService(StorageRepository storageRepository, TestService testService) {
        this.storageRepository = storageRepository;
        this.testService = testService;
    }

    public Optional<Storage> getStorage(Long id) {
        return storageRepository.findById(id);
    }

    public void createStorage(@Valid TestStorageDTO dto) {
        Test test = testService.getTestById(dto.getTestId())
                .orElseThrow(() -> new EntityNotFoundException("El test con ID " + dto.getTestId() + " no existe"));
        Storage storage = new Storage(dto, test);
        storageRepository.save(storage);
    }

}
