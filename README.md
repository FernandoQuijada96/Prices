# Prices API

REST API that retrieves the applicable price for a product and brand at a given date.

## Technologies

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- H2 (in-memory database)
- Lombok
- Maven

## Hexagonal Architecture

```
src/main/java/com/example/prices/
├── PricesApplication.java
├── domain/
│   ├── model/
│   │   └── Price.java                          # Pure domain record
│   └── port/
│       ├── in/
│       │   └── GetPriceUseCase.java            # Input port (use case)
│       └── out/
│           └── PriceRepositoryPort.java        # Output port (persistence contract)
├── application/
│   └── service/
│       └── PriceServiceImpl.java               # Use case implementation
└── infrastructure/
    └── adapter/
        ├── in/
        │   └── rest/
        │       ├── PriceController.java        # REST input adapter
        │       ├── PriceResponse.java          # Response DTO
        │       └── PriceResponseMapper.java    # Mapper Price → PriceResponse
        └── out/
            └── persistence/
                ├── PriceEntity.java            # JPA entity
                ├── PriceJpaRepository.java     # Spring Data repository
                ├── PriceEntityMapper.java      # Mapper PriceEntity → Price
                └── PriceRepositoryAdapter.java # Output port implementation
```

### Dependency rule

```
infrastructure  →  application  →  domain
(adapters)         (use cases)     (model + ports)
```

The domain **has no external dependencies**. Adapters depend on the domain through ports.

## Prerequisites

- Java 21+
- Maven 3.9+

## Running the application

```bash
mvn spring-boot:run
```

The application starts at `http://localhost:8080`.

## Endpoint

### Get applicable price

```
GET /api/prices?applicationDate={date}&productId={id}&brandId={id}
```

**Example request:**

```
GET /api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1
```

**Example response (200 OK):**

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

If no applicable price is found, **404 Not Found** is returned.

When multiple price lists match the same date, the one with the highest **priority** is applied.
