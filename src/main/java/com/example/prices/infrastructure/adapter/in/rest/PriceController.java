package com.example.prices.infrastructure.adapter.in.rest;

import com.example.prices.domain.port.in.GetPriceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam("applicationDate") LocalDateTime applicationDate,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId) {

        return getPriceUseCase.getApplicablePrice(applicationDate, productId, brandId)
                .map(PriceResponseMapper::toResponse)
                .map(price -> ResponseEntity.ok(price))
                .orElse(ResponseEntity.notFound().build());
    }
}
