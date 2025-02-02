package com.prebel.prototipo.webapp.services;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;

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

