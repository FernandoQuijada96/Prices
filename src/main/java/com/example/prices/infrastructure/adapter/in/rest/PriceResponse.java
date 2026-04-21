package com.example.prices.infrastructure.adapter.in.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        Integer productId,
        Integer brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {
}