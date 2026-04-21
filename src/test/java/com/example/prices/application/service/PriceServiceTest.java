package com.example.prices.application.service;

import com.example.prices.domain.exception.PriceNotFoundException;
import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.out.PriceRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private PriceService priceService;

    private static final Integer BRAND_ID = 1;
    private static final Integer PRODUCT_ID = 35455;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0);

    private Price buildPrice(Integer priceList, Integer priority, BigDecimal amount) {
        return new Price(PRODUCT_ID, BRAND_ID, priceList,
                APPLICATION_DATE.minusHours(1), APPLICATION_DATE.plusHours(1),
                amount, priority, "EUR");
    }

    @Nested
    @DisplayName("getApplicablePrice - success cases")
    class SuccessCases {

        @Test
        @DisplayName("Should return the only matching price")
        void shouldReturnSingleMatchingPrice() {
            Price price = buildPrice(1, 0, new BigDecimal("35.50"));
            when(priceRepositoryPort.findPricesByBrandProductAndDate(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                    .thenReturn(List.of(price));

            Price result = priceService.getApplicablePrice(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);

            assertThat(result).isEqualTo(price);
        }

        @Test
        @DisplayName("Should return the price with the highest priority when multiple prices match")
        void shouldReturnHighestPriorityPrice() {
            Price lowPriority = buildPrice(1, 0, new BigDecimal("35.50"));
            Price highPriority = buildPrice(2, 1, new BigDecimal("25.45"));
            when(priceRepositoryPort.findPricesByBrandProductAndDate(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                    .thenReturn(List.of(lowPriority, highPriority));

            Price result = priceService.getApplicablePrice(APPLICATION_DATE, PRODUCT_ID, BRAND_ID);

            assertThat(result).isEqualTo(highPriority);
            assertThat(result.priority()).isEqualTo(1);
            assertThat(result.price()).isEqualByComparingTo("25.45");
        }
    }

    @Nested
    @DisplayName("getApplicablePrice - error cases")
    class ErrorCases {

        @Test
        @DisplayName("Should throw PriceNotFoundException when no prices match")
        void shouldThrowPriceNotFoundExceptionWhenNoPricesFound() {
            when(priceRepositoryPort.findPricesByBrandProductAndDate(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                    .thenReturn(List.of());

            assertThatThrownBy(() -> priceService.getApplicablePrice(APPLICATION_DATE, PRODUCT_ID, BRAND_ID))
                    .isInstanceOf(PriceNotFoundException.class)
                    .satisfies(ex -> {
                        PriceNotFoundException e = (PriceNotFoundException) ex;
                        assertThat(e.getBrandId()).isEqualTo(BRAND_ID);
                        assertThat(e.getProductId()).isEqualTo(PRODUCT_ID);
                    });
        }
    }
}

