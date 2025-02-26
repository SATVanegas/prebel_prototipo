package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestStorageDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.StorageRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    public Optional<Storage> getStorage(Long id) {
        return storageRepository.findById(id);
    }

    public void createStorage(@Valid TestStorageDTO dto) {
        Storage storage = new Storage(dto);
        storageRepository.save(storage);
    }

}
