package com.example.prices.infrastructure.adapter.in.rest;

import com.example.prices.domain.exception.PriceNotFoundException;
import com.example.prices.domain.model.Price;
import com.example.prices.domain.port.in.GetPriceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PriceControllerTest {

    private static final String API_PRICES_URL = "/api/prices";
    private static final Integer PRODUCT_ID = 35455;
    private static final Integer BRAND_ID = 1;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockitoBean
    private GetPriceUseCase getPriceUseCase;

    private Price buildPrice(Integer priceList, BigDecimal amount, LocalDateTime start, LocalDateTime end) {
        return new Price(PRODUCT_ID, BRAND_ID, priceList, start, end, amount, 0, "EUR");
    }

    @Test
    @DisplayName("Test 1: Request at 10:00 on day 14 should return price list 1 with price 35.50")
    void givenDay14At10_whenGetPrice_thenReturnsPriceList1WithPrice3550() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price price = buildPrice(1, new BigDecimal("35.50"),
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59));

        when(getPriceUseCase.getApplicablePrice(date, PRODUCT_ID, BRAND_ID)).thenReturn(price);

        mockMvc.perform(get(API_PRICES_URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 2: Request at 16:00 on day 14 should return price list 2 with price 25.45")
    void givenDay14At16_whenGetPrice_thenReturnsPriceList2WithPrice2545() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Price price = buildPrice(2, new BigDecimal("25.45"),
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30));

        when(getPriceUseCase.getApplicablePrice(date, PRODUCT_ID, BRAND_ID)).thenReturn(price);

        mockMvc.perform(get(API_PRICES_URL)
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on day 14 should return price list 1 with price 35.50")
    void givenDay14At21_whenGetPrice_thenReturnsPriceList1WithPrice3550() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        Price price = buildPrice(1, new BigDecimal("35.50"),
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59));

        when(getPriceUseCase.getApplicablePrice(date, PRODUCT_ID, BRAND_ID)).thenReturn(price);

        mockMvc.perform(get(API_PRICES_URL)
                        .param("applicationDate", "2020-06-14T21:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on day 15 should return price list 3 with price 30.50")
    void givenDay15At10_whenGetPrice_thenReturnsPriceList3WithPrice3050() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        Price price = buildPrice(3, new BigDecimal("30.50"),
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0));

        when(getPriceUseCase.getApplicablePrice(date, PRODUCT_ID, BRAND_ID)).thenReturn(price);

        mockMvc.perform(get(API_PRICES_URL)
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on day 16 should return price list 4 with price 38.95")
    void givenDay16At21_whenGetPrice_thenReturnsPriceList4WithPrice3895() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
        Price price = buildPrice(4, new BigDecimal("38.95"),
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59));

        when(getPriceUseCase.getApplicablePrice(date, PRODUCT_ID, BRAND_ID)).thenReturn(price);

        mockMvc.perform(get(API_PRICES_URL)
                        .param("applicationDate", "2020-06-16T21:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    @DisplayName("Should return 404 with ProblemDetail body when no price is found")
    void givenNoMatchingPrice_whenGetPrice_thenReturns404WithBody() throws Exception {
        LocalDateTime date = LocalDateTime.of(2019, 1, 1, 0, 0);

        when(getPriceUseCase.getApplicablePrice(date, PRODUCT_ID, BRAND_ID))
                .thenThrow(new PriceNotFoundException(BRAND_ID, PRODUCT_ID));

        mockMvc.perform(get(API_PRICES_URL)
                        .param("applicationDate", "2019-01-01T00:00:00")
                        .param("productId", PRODUCT_ID.toString())
                        .param("brandId", BRAND_ID.toString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Price Not Found"));
    }
}

