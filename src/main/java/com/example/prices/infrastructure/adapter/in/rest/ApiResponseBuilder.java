package com.example.prices.infrastructure.adapter.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ApiResponseBuilder {

    public static ResponseEntity<ErrorResponse> badRequest(String message) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Bad Request", message));
    }

    public static ResponseEntity<ErrorResponse> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), "Not Found", message));
    }

}