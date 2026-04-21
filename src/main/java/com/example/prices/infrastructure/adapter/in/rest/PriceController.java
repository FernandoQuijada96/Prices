package com.example.prices.infrastructure.adapter.in.rest;

import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.in.GetPriceUseCase;
import org.springframework.format.annotation.DateTimeFormat;
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
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("productId") Integer productId,
            @RequestParam("brandId") Integer brandId) {

        Price price = getPriceUseCase.getApplicablePrice(applicationDate, productId, brandId);
        return ResponseEntity.ok(PriceResponseMapper.toResponse(price));
    }
}
