package com.example.prices.service;


import com.example.prices.dto.PriceResponse;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {
    Optional<PriceResponse> getApplicablePrice(LocalDateTime applicationDate, Integer productId, Integer brandId);
}
