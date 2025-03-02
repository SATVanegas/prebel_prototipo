package com.prebel.prototipo.webapp.models.laboratory_reports;

import lombok.Getter;

@Getter
public enum EnumTest {
    APPEARANCE("Appearance"),
    COLOR("Color"),
    FUNGI_YEAST_COUNT("Fungal Yeast Count"),
    ODOR("Odor"),
    PATHOGENS("Pathogens"),
    PH("pH Level"),
    SPECIFIC_GRAVITY("Specific Gravity"),
    TOTAL_BACTERIA_COUNT("Total Bacteria Count"),
    VISCOSITY("Viscosity");

    private final String description;

    EnumTest(String description) {
        this.description = description;
    }

}

