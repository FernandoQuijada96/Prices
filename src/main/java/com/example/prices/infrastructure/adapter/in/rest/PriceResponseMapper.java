package com.example.prices.infrastructure.adapter.in.rest;

import com.example.prices.domain.model.Price;

public class PriceResponseMapper {

    private PriceResponseMapper() {
    }

    public static PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.startDate(),
                price.endDate(),
                price.price()
        );
    }
}

