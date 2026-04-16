package com.example.prices.domain.port.in;

import com.example.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GetPriceUseCase {
    Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Integer productId, Integer brandId);
}

