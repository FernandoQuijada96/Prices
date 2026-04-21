package com.example.prices.infrastructure.adapter.in.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.BeforeEach;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("local")
class PriceControllerIntegrationTest {

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

    @Nested
    class AcceptanceTests {

        @Test
        @DisplayName("Test 1: Request at 10:00 on day 14 should return price list 1 with price 35.50")
        void test1_day14At10_shouldReturnPriceList1() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                    .andExpect(jsonPath("$.brandId").value(BRAND_ID))
                    .andExpect(jsonPath("$.priceList").value(1))
                    .andExpect(jsonPath("$.price").value(35.50));
        }

        @Test
        @DisplayName("Test 2: Request at 16:00 on day 14 should return price list 2 with price 25.45")
        void test2_day14At16_shouldReturnPriceList2() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T16:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                    .andExpect(jsonPath("$.brandId").value(BRAND_ID))
                    .andExpect(jsonPath("$.priceList").value(2))
                    .andExpect(jsonPath("$.price").value(25.45));
        }

        @Test
        @DisplayName("Test 3: Request at 21:00 on day 14 should return price list 1 with price 35.50")
        void test3_day14At21_shouldReturnPriceList1() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T21:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                    .andExpect(jsonPath("$.brandId").value(BRAND_ID))
                    .andExpect(jsonPath("$.priceList").value(1))
                    .andExpect(jsonPath("$.price").value(35.50));
        }

        @Test
        @DisplayName("Test 4: Request at 10:00 on day 15 should return price list 3 with price 30.50")
        void test4_day15At10_shouldReturnPriceList3() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-15T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                    .andExpect(jsonPath("$.brandId").value(BRAND_ID))
                    .andExpect(jsonPath("$.priceList").value(3))
                    .andExpect(jsonPath("$.price").value(30.50));
        }

        @Test
        @DisplayName("Test 5: Request at 21:00 on day 16 should return price list 4 with price 38.95")
        void test5_day16At21_shouldReturnPriceList4() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-16T21:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.productId").value(PRODUCT_ID))
                    .andExpect(jsonPath("$.brandId").value(BRAND_ID))
                    .andExpect(jsonPath("$.priceList").value(4))
                    .andExpect(jsonPath("$.price").value(38.95));
        }
    }

    @Nested
    @DisplayName("Error Handling Tests - HTTP 400 Bad Request")
    class BadRequestTests {

        @Test
        @DisplayName("Should return 400 when applicationDate parameter is missing")
        void shouldReturn400WhenApplicationDateIsMissing() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.title").value("Missing Parameter"))
                    .andExpect(jsonPath("$.detail").value("Required parameter 'applicationDate' is missing"));
        }

        @Test
        @DisplayName("Should return 400 when productId parameter is missing")
        void shouldReturn400WhenProductIdIsMissing() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.title").value("Missing Parameter"))
                    .andExpect(jsonPath("$.detail").value("Required parameter 'productId' is missing"));
        }

        @Test
        @DisplayName("Should return 400 when brandId parameter is missing")
        void shouldReturn400WhenBrandIdIsMissing() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", PRODUCT_ID.toString()))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.title").value("Missing Parameter"))
                    .andExpect(jsonPath("$.detail").value("Required parameter 'brandId' is missing"));
        }

        @Test
        @DisplayName("Should return 400 when applicationDate has invalid format")
        void shouldReturn400WhenApplicationDateHasInvalidFormat() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "invalid-date")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.title").value("Invalid Parameter"))
                    .andExpect(jsonPath("$.detail").value("Parameter 'applicationDate' must be of type LocalDateTime"));
        }

        @Test
        @DisplayName("Should return 400 when productId is not a number")
        void shouldReturn400WhenProductIdIsNotANumber() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", "not-a-number")
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.title").value("Invalid Parameter"))
                    .andExpect(jsonPath("$.detail").value("Parameter 'productId' must be of type Integer"));
        }

        @Test
        @DisplayName("Should return 400 when brandId is not a number")
        void shouldReturn400WhenBrandIdIsNotANumber() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", "not-a-number"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.title").value("Invalid Parameter"))
                    .andExpect(jsonPath("$.detail").value("Parameter 'brandId' must be of type Integer"));
        }
    }

    @Nested
    @DisplayName("Error Handling Tests - HTTP 404 Not Found")
    class NotFoundTests {

        @Test
        @DisplayName("Should return 404 when no price is found for the given criteria")
        void shouldReturn404WhenNoPriceFound() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2019-01-01T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.title").value("Price Not Found"))
                    .andExpect(jsonPath("$.detail").value("No price found for brandId=1 and productId=35455"));
        }

        @Test
        @DisplayName("Should return 404 when product does not exist")
        void shouldReturn404WhenProductDoesNotExist() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", "99999")
                            .param("brandId", BRAND_ID.toString()))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.title").value("Price Not Found"))
                    .andExpect(jsonPath("$.detail").value("No price found for brandId=1 and productId=99999"));
        }

        @Test
        @DisplayName("Should return 404 when brand does not exist")
        void shouldReturn404WhenBrandDoesNotExist() throws Exception {
            mockMvc.perform(get(API_PRICES_URL)
                            .param("applicationDate", "2020-06-14T10:00:00")
                            .param("productId", PRODUCT_ID.toString())
                            .param("brandId", "99"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.title").value("Price Not Found"))
                    .andExpect(jsonPath("$.detail").value("No price found for brandId=99 and productId=35455"));
        }
    }
}

