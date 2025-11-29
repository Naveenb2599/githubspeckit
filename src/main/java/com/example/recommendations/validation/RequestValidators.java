package com.example.recommendations.validation;

import java.time.LocalDate;

public class RequestValidators {
    public static void validateIata(String code) {
        if (code == null || code.isBlank() || code.length() != 3) {
            throw new IllegalArgumentException("IATA code must be 3 characters: " + code);
        }
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid date format, expected YYYY-MM-DD: " + dateStr);
        }
    }
}
