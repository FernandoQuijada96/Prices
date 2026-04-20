package com.example.prices.domain.port.out;

import com.example.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findPricesByBrandProductAndDate(Integer brandId, Integer productId, LocalDateTime applicationDate);
}

