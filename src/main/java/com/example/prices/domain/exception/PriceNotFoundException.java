package com.example.prices.domain.exception;

public class PriceNotFoundException extends RuntimeException {

    private final Integer brandId;
    private final Integer productId;

    public PriceNotFoundException(Integer brandId, Integer productId) {
        super(String.format("No price found for brandId=%d and productId=%d", brandId, productId));
        this.brandId = brandId;
        this.productId = productId;
    }

    public Integer getBrandId() { return brandId; }
    public Integer getProductId() { return productId; }
}
