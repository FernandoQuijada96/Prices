# Prices API

REST API to get the applicable price for a product at a given date.

## Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- H2 Database
- Maven

## Project Structure (Hexagonal Architecture)

```
src/main/java/com/example/prices/
├── domain/
│   ├── model/Price.java                 # Domain entity
│   ├── exception/                       # Domain exceptions
│   └── port/
│       ├── in/GetPriceUseCase.java      # Input port
│       └── out/PriceRepositoryPort.java # Output port
├── application/
│   └── service/PriceService.java        # Business logic
└── infrastructure/
    └── adapter/
        ├── in/rest/                     # REST controller & DTOs
        └── out/persistence/             # JPA repository & entities
```

## API

### GET /api/prices

**Parameters:**
- `applicationDate` - DateTime (e.g., `2020-06-14T10:00:00`)
- `productId` - Integer
- `brandId` - Integer

**Success Response (200):**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50
}
```

**Error Responses:**
- `400 Bad Request` - Missing or invalid parameters
- `404 Not Found` - No price found for criteria

