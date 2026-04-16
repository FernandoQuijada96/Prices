package com.example.prices.infrastructure.adapter.out.persistence;

import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.out.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class JpaPriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;

    public JpaPriceRepositoryAdapter(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public Optional<Price> findApplicablePrice(Integer brandId, Integer productId, LocalDateTime applicationDate) {
        return priceJpaRepository.findApplicablePrice(brandId, productId, applicationDate)
                .map(PriceEntityMapper::toDomain);
    }
}

