package com.example.prices.domain.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Integer brandId, Integer productId) {
        super(String.format("No price found for brandId=%d and productId=%d", brandId, productId));
    }
}

