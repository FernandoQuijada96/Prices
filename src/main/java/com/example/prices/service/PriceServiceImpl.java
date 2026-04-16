package com.example.prices.service;


import com.example.prices.dto.PriceResponse;
import com.example.prices.mapper.PriceMapper;
import com.example.prices.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<PriceResponse> getApplicablePrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        return priceRepository
                .findApplicablePrice(brandId, productId, applicationDate)
                .map(PriceMapper::toResponse);
    }
}
