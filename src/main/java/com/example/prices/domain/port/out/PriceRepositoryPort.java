package com.example.prices.domain.port.out;

import com.example.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findApplicablePrice(Integer brandId, Integer productId, LocalDateTime applicationDate);
}

