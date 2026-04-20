package com.example.prices.infrastructure.adapter.in.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public final class RequestValidator {

    private RequestValidator() {}

    public static void requireNonBlank(String value, String paramName) throws ValidationException {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Required parameter '" + paramName + "' is missing");
        }
    }

    public static LocalDateTime parseLocalDateTime(String value, String paramName) throws ValidationException {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Parameter '" + paramName + "' must be of type LocalDateTime");
        }
    }

    public static Integer parseInteger(String value, String paramName) throws ValidationException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ValidationException("Parameter '" + paramName + "' must be of type Integer");
        }
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}

