package com.prebel.prototipo.webapp.services;

import com.prebel.prototipo.webapp.dtos.validations.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.TestRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.*;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TestService {

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

    public TestService(TestRepository testRepository, AppearanceRepository appearanceRepository,
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


    public void createTest(TestDTO testDTO) {
            Test test = new Test();

            test.setTemperature(temperatureRepository.findById(testDTO.getTemperatureId())
                    .orElseThrow(() -> new IllegalArgumentException("Temperature not found")));
            test.setColor(colorRepository.findById(testDTO.getColorId())
                    .orElseThrow(() -> new IllegalArgumentException("Color not found")));
            test.setOdor(odorRepository.findById(testDTO.getOdorId())
                    .orElseThrow(() -> new IllegalArgumentException("Odor not found")));
            test.setAppearance(appearanceRepository.findById(testDTO.getAppearanceId())
                    .orElseThrow(() -> new IllegalArgumentException("Appearance not found")));
            test.setPh(phRepository.findById(testDTO.getPhId())
                    .orElseThrow(() -> new IllegalArgumentException("pH not found")));
            test.setViscosity(viscosityRepository.findById(testDTO.getViscosityId())
                    .orElseThrow(() -> new IllegalArgumentException("Viscosity not found")));
            test.setSpecificGravity(specificGravityRepository.findById(testDTO.getSpecificGravityId())
                    .orElseThrow(() -> new IllegalArgumentException("Specific gravity not found")));
            test.setTotalBacteriaCount(totalBacteriaCountRepository.findById(testDTO.getTotalBacteriaCountId())
                    .orElseThrow(() -> new IllegalArgumentException("Total bacteria count not found")));
            test.setFungiYeastCount(fungiYeastCountRepository.findById(testDTO.getFungiYeastCountId())
                    .orElseThrow(() -> new IllegalArgumentException("Fungi yeast count not found")));
            test.setPathogens(pathogensRepository.findById(testDTO.getPathogensId())
                    .orElseThrow(() -> new IllegalArgumentException("Pathogens not found")));
            test.setStorage(storageRepository.findById(testDTO.getStorageId())
                    .orElseThrow(() -> new IllegalArgumentException("Storage not found")));
            test.setUserOrganolepticTests(userRepository.findById(testDTO.getUserOrganolepticTestsId())
                    .orElseThrow(() -> new IllegalArgumentException("User Organoleptic Tests not found")));
            test.setUserPhysicochemicalTests(userRepository.findById(testDTO.getUserPhysicochemicalTestsId())
                    .orElseThrow(() -> new IllegalArgumentException("User Physicochemical Tests not found")));
            test.setObservations(testDTO.getObservations());
            test.setConclusion(testDTO.getConclusion());
            test.setProduct(productRepository.findById(testDTO.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found")));

        testRepository.save(test);
    }
}

