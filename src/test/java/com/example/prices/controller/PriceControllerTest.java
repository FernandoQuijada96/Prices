package com.example.prices.controller;

import com.example.prices.dto.PriceResponse;
import com.example.prices.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    private static final Integer PRODUCT_ID = 35455;
    private static final Integer BRAND_ID = 1;

    @Mock
    private PriceService priceService;
    @InjectMocks
    private PriceController priceController;

    @Test
    void givenDay14At10_whenGetPrice_thenReturnsPriceList1WithPrice3550() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        PriceResponse expected = new PriceResponse(PRODUCT_ID, BRAND_ID, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new BigDecimal("35.50"));

        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.of(expected));

        // When
        ResponseEntity<PriceResponse> response = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().priceList()).isEqualTo(1);
        assertThat(response.getBody().price()).isEqualByComparingTo("35.50");
    }

    @Test
    void givenDay14At16_whenGetPrice_thenReturnsPriceList2WithPrice2545() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        PriceResponse expected = new PriceResponse(PRODUCT_ID, BRAND_ID, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                new BigDecimal("25.45"));

        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.of(expected));

        // When
        ResponseEntity<PriceResponse> response = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().priceList()).isEqualTo(2);
        assertThat(response.getBody().price()).isEqualByComparingTo("25.45");
    }

    @Test
    void givenDay14At21_whenGetPrice_thenReturnsPriceList1WithPrice3550() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        PriceResponse expected = new PriceResponse(PRODUCT_ID, BRAND_ID, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new BigDecimal("35.50"));

        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.of(expected));

        // When
        ResponseEntity<PriceResponse> response = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().priceList()).isEqualTo(1);
        assertThat(response.getBody().price()).isEqualByComparingTo("35.50");
    }

    @Test
    void givenDay15At10_whenGetPrice_thenReturnsPriceList3WithPrice3050() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        PriceResponse expected = new PriceResponse(PRODUCT_ID, BRAND_ID, 3,
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0),
                new BigDecimal("30.50"));

        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.of(expected));

        // When
        ResponseEntity<PriceResponse> response = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().priceList()).isEqualTo(3);
        assertThat(response.getBody().price()).isEqualByComparingTo("30.50");
    }

    @Test
    void givenDay16At21_whenGetPrice_thenReturnsPriceList4WithPrice3895() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
        PriceResponse expected = new PriceResponse(PRODUCT_ID, BRAND_ID, 4,
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                new BigDecimal("38.95"));

        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.of(expected));

        // When
        ResponseEntity<PriceResponse> response = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().priceList()).isEqualTo(4);
        assertThat(response.getBody().price()).isEqualByComparingTo("38.95");
    }

    @Test
    void givenNoMatchingPrice_whenGetPrice_thenReturns404() {
        // Given
        LocalDateTime date = LocalDateTime.of(2019, 1, 1, 0, 0);

        when(priceService.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<PriceResponse> response = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
