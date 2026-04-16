package com.example.prices.mapper;

import com.example.prices.dto.PriceResponse;
import com.example.prices.model.PriceEntity;

public class PriceMapper {

    private PriceMapper() {
    }

    public static PriceResponse toResponse(PriceEntity entity) {
        return new PriceResponse(
                entity.getProductId(),
                entity.getBrandId(),
                entity.getPriceList(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice()
        );
    }
}

