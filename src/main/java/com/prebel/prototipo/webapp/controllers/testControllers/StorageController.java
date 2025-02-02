package com.prebel.prototipo.webapp.controllers.testControllers;

import com.prebel.prototipo.webapp.models.tests.Storage;
import com.prebel.prototipo.webapp.repositories.testRepositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/storage")
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Storage> getStorageById(@PathVariable Long id) {
        Optional<Storage> storage = storageRepository.findById(id);
        return storage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Storage
    @PostMapping
    public ResponseEntity<Storage> createStorage(@RequestBody Storage storage) {
        if (storage.getEquipmentCode() == null || storage.getDescription() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        Storage savedStorage = storageRepository.save(storage);
        return ResponseEntity.ok(savedStorage);
    }
}
