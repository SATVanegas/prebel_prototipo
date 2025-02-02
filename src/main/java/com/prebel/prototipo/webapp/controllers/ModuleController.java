package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.repositories.ModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prebel.prototipo.webapp.models.permissions.Module;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    ModuleRepository moduleRepository;

    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }


    @PostMapping()
    public ResponseEntity<Module> createModule(@RequestBody Module request) {
        Module module = new Module();
        module.setName(request.getName());
        module.setDescription(request.getDescription());
        moduleRepository.save(module);
        return ResponseEntity.status(HttpStatus.CREATED).body(module);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Module>> getModules() {
        return ResponseEntity.ok(moduleRepository.findAll());
    }




}
