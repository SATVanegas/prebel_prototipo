package com.prebel.prototipo.webapp.services.utils;

import java.util.*;

import com.prebel.prototipo.webapp.models.role_module.User;
import org.springframework.stereotype.Service;

import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.*;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ConditionRepository;

@Service
public class DefaultProductService {
    private final ConditionRepository conditionRepository;
    private final Random random;
    private static final List<String> COSMETIC_PRODUCTS = Arrays.asList("Crema Hidratante Facial", "Sérum Antiarrugas", "Tónico Facial Refrescante", "Protector Solar SPF 50", "Base de Maquillaje Líquida", "Corrector de Ojeras", "Rubor Compacto", "Labial Mate", "Brillo Labial Hidratante", "Delineador de Ojos en Gel", "Sombras de Ojos Nude", "Máscara de Pestañas Voluminizadora", "Esmalte de Uñas en Gel", "Crema para Manos con Karité", "Aceite Corporal Nutritivo", "Loción Corporal Perfumada", "Jabón Facial Purificante", "Exfoliante Corporal de Café", "Shampoo Fortalecedor", "Acondicionador Hidratante", "Mascarilla Capilar Reparadora", "Spray Fijador para el Cabello", "Perfume Floral", "Desodorante Natural", "Bálsamo Labial con SPF", "Crema Anticelulítica", "Serum Capilar de Argán");
    private static final List<String> MARCAS_DERMATOLOGICAS = List.of("La Roche-Posay", "CeraVe", "Vichy", "Bioderma", "Eucerin", "Avene", "Neutrogena", "SkinCeuticals", "ISDIN", "Sesderma", "Dermalogica", "Clinique", "Obagi", "Paula’s Choice", "The Ordinary");
    private static final List<String> NAMES = Arrays.asList("Juan Pérez", "María Gómez", "Carlos Rodríguez", "Ana Fernández", "Luis Martínez");
    private static final List<String> EMAILS = Arrays.asList("juan@example.com", "maria@example.com", "carlos@example.com", "ana@example.com", "luis@example.com");
    private static final List<String> PHONE_NUMBERS = Arrays.asList("555-1234", "555-5678", "555-9101", "555-1213", "555-1415");

    public DefaultProductService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
        this.random = new Random();
    }

    public Product crearProductoDePrueba() {
        Product product = new Product();
        product.setProductDescription(obtenerNombreAleatorio());
        product.setReference("REF-" + generarValorAleatorio(1000, 9999));
        product.setBatch("Lote-" + generarValorAleatorio(1, 100));
        product.setPackagingType("Botella");
        product.setPackagingMaterial("Plástico");
        product.setContainerColor("Transparente");
        product.setLidMaterial("Polipropileno");
        product.setLidColor("Blanco");
        product.setFormulaNumber("13456728");
        product.setProjectCode("PROJ-" + generarValorAleatorio(100, 999));
        product.setProjectName("Proyecto " + generarValorAleatorio(1, 50));
        product.setBrand(obtenerMarcaAleatoria());
        product.setStudyType("Estabilidad acelerada");
        product.setConsecutive(generarValorAleatorio(1, 1000));
        product.setJustification("Evaluación de estabilidad según normas.");
        product.setQualification("Calificación inicial");
        product.setEstablishedValidity("Válido por " + generarValorAleatorio(12, 48) + " meses");

        asignarResponsables(product);

        // Fechas de estudio
        Date currentDate = new Date();
        product.setStartDate(currentDate);
        product.setStudyDuration(generarValorAleatorio(6, 30));
        product.setFinishDate(new Date(currentDate.getTime() + (long) product.getStudyDuration() * 30 * 24 * 60 * 60 * 1000));

        // Generar tests
        product.setTests(crearTests(product, generarValorAleatorio(2, 4)));

        return product;
    }

    private void asignarResponsables(Product product) {
        product.setCustomer(createRandomUser());
        product.setResponsibleChemist(createRandomUser());
        product.setResponsibleEngineer(createRandomUser());
        product.setResponsibleAnalyst(createRandomUser());
        product.setTechnicianInCharge(createRandomUser());
    }

    public User createRandomUser() {
        User user = new User();
        user.setName(NAMES.get(random.nextInt(NAMES.size())));
        user.setEmail(EMAILS.get(random.nextInt(EMAILS.size())));
        user.setNumber(PHONE_NUMBERS.get(random.nextInt(PHONE_NUMBERS.size())));
        user.setPassword(String.valueOf(123));

        return user;
    }

    private List<Test> crearTests(Product product, int cantidad) {
        List<Test> tests = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            tests.add(crearTest(product));
        }
        return tests;
    }

    public Test crearTest(Product product) {
        Test test = new Test();
        test.setProduct(product);
        test.setTemperature(crearTemperature());
        test.setStorage(crearStorage());
        test.setConditions(crearConditions());
        test.setObservations(generarObservacion());
        test.setConclusion(generarConclusion());
        return test;
    }

    public Temperature crearTemperature() {
        Temperature temperature = new Temperature();
        temperature.setUnit("°C");
        temperature.setTime(generarValorAleatorio(1, 90));
        temperature.setEquipment(generarValorAleatorio(1, 50));
        return temperature;
    }

    public Storage crearStorage() {
        Storage storage = new Storage();
        int minTemp = generarTemperaturaAleatoria();
        int maxTemp = minTemp + generarValorAleatorio(1, 10);

        storage.setMinTemperature(minTemp);
        storage.setMaxTemperature(maxTemp);
        storage.setEquipmentCode("EQ-" + generarValorAleatorio(1000, 9999));
        storage.setDescription("Almacén de temperatura controlada entre " + minTemp + "°C y " + maxTemp + "°C");

        return storage;
    }

    private List<Condition> crearConditions() {
        return (List<Condition>) conditionRepository.saveAll(List.of(
                crearCondition(EnumTest.COLOR), crearCondition(EnumTest.ODOR),
                crearCondition(EnumTest.APPEARANCE), crearCondition(EnumTest.PH),
                crearCondition(EnumTest.VISCOSITY), crearCondition(EnumTest.SPECIFIC_GRAVITY),
                crearCondition(EnumTest.TOTAL_BACTERIA_COUNT), crearCondition(EnumTest.FUNGI_YEAST_COUNT),
                crearCondition(EnumTest.PATHOGENS)
        ));
    }

    private Condition crearCondition(EnumTest tipo) {
        Condition condition = new Condition();
        condition.setType(tipo);

        switch (tipo) {
            case COLOR -> asignarDetallesCondicion(condition, "Escala de color", "Incoloro a ligeramente amarillo", "Comparación visual");
            case ODOR -> asignarDetallesCondicion(condition, "Intensidad", "Inodoro o leve", "Evaluación sensorial");
            case APPEARANCE -> asignarDetallesCondicion(condition, "Aspecto visual", "Líquido claro sin partículas", "Inspección visual");
            case PH -> asignarDetallesCondicion(condition, "pH Units", "6.5 - 7.5", "Método potenciométrico");
            case VISCOSITY -> asignarDetallesCondicion(condition, "cP (centipoise)", "1.0 - 5.0 cP", "Viscosímetro rotacional");
            case SPECIFIC_GRAVITY -> asignarDetallesCondicion(condition, "g/cm³", "0.95 - 1.05", "Densímetro digital");
            case TOTAL_BACTERIA_COUNT -> asignarDetallesCondicion(condition, "UFC/mL", "Menos de 1000 UFC/mL", "Cultivo en agar");
            case FUNGI_YEAST_COUNT -> asignarDetallesCondicion(condition, "UFC/mL", "Menos de 100 UFC/mL", "Cultivo en agar Sabouraud");
            case PATHOGENS -> asignarDetallesCondicion(condition, "Presencia/ausencia", "Ausencia", "PCR o cultivo selectivo");
        }

        condition.setInitialResultsDevelopmentLaboratory(generarResultado(tipo));
        condition.setInitialResultsStabilityLaboratory(generarResultado(tipo));
        condition.setTime(generarValorAleatorio(1, 90));
        condition.setEquipment(generarValorAleatorio(1, 50));

        return condition;
    }

    private void asignarDetallesCondicion(Condition condition, String unidad, String especificacion, String metodo) {
        condition.setUnit(unidad);
        condition.setSpecification(especificacion);
        condition.setMethod(metodo);
    }

    private String generarResultado(EnumTest tipo) {
        return switch (tipo) {
            case COLOR -> random.nextBoolean() ? "Incoloro" : "Amarillo claro";
            case ODOR -> random.nextBoolean() ? "Inodoro" : "Leve olor característico";
            case APPEARANCE -> random.nextBoolean() ? "Transparente" : "Ligeramente turbio";
            case PH -> "pH = " + (6.5 + random.nextDouble()); // Entre 6.5 y 7.5
            case VISCOSITY -> "Viscosidad = " + (1.0 + random.nextDouble() * 4.0) + " cP"; // Entre 1.0 y 5.0
            case SPECIFIC_GRAVITY -> "Densidad = " + (0.95 + random.nextDouble() * 0.1) + " g/cm³"; // Entre 0.95 y 1.05
            case TOTAL_BACTERIA_COUNT -> "Bacterias = " + random.nextInt(1000) + " UFC/mL"; // Hasta 1000 UFC/mL
            case FUNGI_YEAST_COUNT -> "Hongos y levaduras = " + random.nextInt(100) + " UFC/mL"; // Hasta 100 UFC/mL
            case PATHOGENS -> random.nextBoolean() ? "Presencia detectada" : "Ausencia confirmada";
        };
    }

    private String obtenerNombreAleatorio() {
        return COSMETIC_PRODUCTS.get(random.nextInt(COSMETIC_PRODUCTS.size()));
    }

    private String obtenerMarcaAleatoria() {
        return MARCAS_DERMATOLOGICAS.get(random.nextInt(MARCAS_DERMATOLOGICAS.size()));
    }

    private int generarValorAleatorio(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private int generarTemperaturaAleatoria() {
        return generarValorAleatorio(0, 9);
    }

    private String generarObservacion() {
        return random.nextBoolean() ? "Evaluación realizada sin anomalías." : "Se necesitan más pruebas.";
    }

    private String generarConclusion() {
        return random.nextBoolean() ? "Producto aprobado." : "Producto en revisión.";
    }
}
