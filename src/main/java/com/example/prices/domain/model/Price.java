package com.example.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        Integer productId,
        Integer brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        Integer priority
) {
}

