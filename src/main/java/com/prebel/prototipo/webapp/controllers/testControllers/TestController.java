package com.prebel.prototipo.webapp.controllers.testControllers;


import com.prebel.prototipo.webapp.models.Test;
import com.prebel.prototipo.webapp.repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.testRepositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {


    private final TestRepository testRepository;
    private final AppearanceRepository appearanceRepository;
    private final ColorRepository colorRepository;
    private final FungiYeastCountRepository fungiYeastCountRepository;
    private final OdorRepository odorRepository;
    private final PathogensRepository pathogensRepository;
    private final pHRepository phRepository;
    private final SpecificGravityRepository specificGravityRepository;
    private final StorageRepository storageRepository;
    private final TemperatureRepository temperatureRepository;
    private final TotalBacteriaCountRepository totalBacteriaCountRepository;
    private final ViscosityRepository viscosityRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public TestController(TestRepository testRepository, AppearanceRepository appearanceRepository,
                          ColorRepository colorRepository, FungiYeastCountRepository fungiYeastCountRepository,
                          OdorRepository odorRepository, PathogensRepository pathogensRepository,
                          pHRepository phRepository, SpecificGravityRepository specificGravityRepository,
                          StorageRepository storageRepository, TemperatureRepository temperatureRepository,
                          TotalBacteriaCountRepository totalBacteriaCountRepository,
                          ViscosityRepository viscosityRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.appearanceRepository = appearanceRepository;
        this.colorRepository = colorRepository;
        this.fungiYeastCountRepository = fungiYeastCountRepository;
        this.odorRepository = odorRepository;
        this.pathogensRepository = pathogensRepository;
        this.phRepository = phRepository;
        this.specificGravityRepository = specificGravityRepository;
        this.storageRepository = storageRepository;
        this.temperatureRepository = temperatureRepository;
        this.totalBacteriaCountRepository = totalBacteriaCountRepository;
        this.viscosityRepository = viscosityRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Buscar un Test por ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


// Crear un nuevo Test
    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        test.setAppearance(appearanceRepository.findById(test.getAppearance().getId()).orElse(null));
        test.setColor(colorRepository.findById(test.getColor().getId()).orElse(null));
        test.setFungiYeastCount(fungiYeastCountRepository.findById(test.getFungiYeastCount().getId()).orElse(null));
        test.setOdor(odorRepository.findById(test.getOdor().getId()).orElse(null));
        test.setPathogens(pathogensRepository.findById(test.getPathogens().getId()).orElse(null));
        test.setPh(phRepository.findById(test.getPh().getId()).orElse(null));
        test.setSpecificGravity(specificGravityRepository.findById(test.getSpecificGravity().getId()).orElse(null));
        test.setStorage(storageRepository.findById(test.getStorage().getId()).orElse(null));
        test.setTemperature(temperatureRepository.findById(test.getTemperature().getId()).orElse(null));
        test.setTotalBacteriaCount(totalBacteriaCountRepository.findById(test.getTotalBacteriaCount().getId()).orElse(null));
        test.setViscosity(viscosityRepository.findById(test.getViscosity().getId()).orElse(null));
        test.setProduct(productRepository.findById(test.getProduct().getId()).orElse(null));
        test.setUserOrganolepticTests(userRepository.findById(test.getUserOrganolepticTests().getId()).orElse(null));
        test.setUserPhysicochemicalTests(userRepository.findById(test.getUserPhysicochemicalTests().getId()).orElse(null));

        Test savedTest = testRepository.save(test);
        return ResponseEntity.ok(savedTest);
    }

}