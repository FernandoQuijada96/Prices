package com.example.prices.application.service;

import com.example.prices.domain.exception.PriceNotFoundException;
import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.in.GetPriceUseCase;
import com.example.prices.domain.port.out.PriceRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PriceService implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public PriceService(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceRepositoryPort.findPricesByBrandProductAndDate(brandId, productId, applicationDate)
                .stream()
                .max(Comparator.comparing(Price::priority))
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId));
    }
}
