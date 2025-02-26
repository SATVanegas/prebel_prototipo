package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestStorageDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StorageService {
    public void createStorage(@Valid TestStorageDTO dto) {
    }

    public Optional<Storage> getStorage(Long id) {
        return Optional.empty();
    }
}
