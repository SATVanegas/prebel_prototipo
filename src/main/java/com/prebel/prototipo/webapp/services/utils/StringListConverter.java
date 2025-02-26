package com.prebel.prototipo.webapp.services.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<String[], String> {

    @Override
    public String convertToDatabaseColumn(String[] attribute) {
        return attribute == null ? null : String.join(",", attribute);
    }

    @Override
    public String[] convertToEntityAttribute(String dbData) {
        return dbData == null || dbData.isEmpty() ? null : dbData.split(",");
    }
}

