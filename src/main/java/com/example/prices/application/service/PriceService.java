package com.example.prices.application.service;


import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.in.GetPriceUseCase;
import com.example.prices.domain.port.out.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceService implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public PriceService(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Optional<Price> getApplicablePrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceRepositoryPort.findApplicablePrice(brandId, productId, applicationDate);
    }
}
