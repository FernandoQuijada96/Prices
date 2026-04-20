package com.example.prices.infrastructure.adapter.in.rest;

import com.example.prices.domain.exception.PriceNotFoundException;
import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.in.GetPriceUseCase;
import com.example.prices.infrastructure.adapter.in.rest.RequestValidator.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.example.prices.infrastructure.adapter.in.rest.ApiResponseBuilder.badRequest;
import static com.example.prices.infrastructure.adapter.in.rest.ApiResponseBuilder.notFound;
import static com.example.prices.infrastructure.adapter.in.rest.RequestValidator.*;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
            @RequestParam(value = "applicationDate", required = false) String applicationDateStr,
            @RequestParam(value = "productId", required = false) String productIdStr,
            @RequestParam(value = "brandId", required = false) String brandIdStr) {

        try {
            requireNonBlank(applicationDateStr, "applicationDate");
            requireNonBlank(productIdStr, "productId");
            requireNonBlank(brandIdStr, "brandId");

            LocalDateTime applicationDate = parseLocalDateTime(applicationDateStr, "applicationDate");
            Integer productId = parseInteger(productIdStr, "productId");
            Integer brandId = parseInteger(brandIdStr, "brandId");

            Price price = getPriceUseCase.getApplicablePrice(applicationDate, productId, brandId);
            return ResponseEntity.ok(PriceResponseMapper.toResponse(price));

        } catch (ValidationException e) {
            return badRequest(e.getMessage());
        } catch (PriceNotFoundException e) {
            return notFound(e.getMessage());
        }
    }
}
