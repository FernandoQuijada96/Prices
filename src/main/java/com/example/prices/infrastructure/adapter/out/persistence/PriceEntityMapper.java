package com.example.prices.infrastructure.adapter.out.persistence;

import com.example.prices.domain.model.Price;

public class PriceEntityMapper {

    private PriceEntityMapper() {
    }

    public static Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getProductId(),
                entity.getBrandId(),
                entity.getPriceList(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice()
        );
    }
}
