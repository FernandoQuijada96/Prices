# Prices API

API REST desarrollada con **Spring Boot 4** que consulta el precio aplicable de un producto para una marca en una fecha determinada, siguiendo **Arquitectura Hexagonal (Ports & Adapters)**.

## Tecnologías

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- H2 (base de datos en memoria)
- Lombok
- Maven

## Arquitectura Hexagonal

```
src/main/java/com/example/prices/
├── PricesApplication.java
├── domain/
│   ├── model/
│   │   └── Price.java                          # Record de dominio puro
│   └── port/
│       ├── in/
│       │   └── GetPriceUseCase.java            # Puerto de entrada (caso de uso)
│       └── out/
│           └── PriceRepositoryPort.java        # Puerto de salida (contrato de persistencia)
├── application/
│   └── service/
│       └── PriceServiceImpl.java               # Implementación del caso de uso
└── infrastructure/
    └── adapter/
        ├── in/
        │   └── rest/
        │       ├── PriceController.java        # Adaptador REST de entrada
        │       ├── PriceResponse.java          # DTO de respuesta
        │       └── PriceResponseMapper.java    # Mapper Price → PriceResponse
        └── out/
            └── persistence/
                ├── PriceEntity.java            # Entidad JPA
                ├── PriceJpaRepository.java     # Repositorio Spring Data
                ├── PriceEntityMapper.java      # Mapper PriceEntity → Price
                └── PriceRepositoryAdapter.java # Implementación del puerto de salida
```

### Regla de dependencia

```
infrastructure  →  application  →  domain
(adapters)         (use cases)     (model + ports)
```

El dominio **no depende de nada externo**. Los adaptadores dependen del dominio a través de los puertos.

## Requisitos previos

- Java 21+
- Maven 3.9+

## Ejecución

```bash
mvn spring-boot:run
```

La aplicación se inicia en `http://localhost:8080`.

## Endpoint

### Consultar precio aplicable

```
GET /api/prices?applicationDate={fecha}&productId={id}&brandId={id}
```

| Parámetro         | Tipo             | Descripción                    |
|-------------------|------------------|--------------------------------|
| `applicationDate` | `LocalDateTime`  | Fecha de aplicación (ISO 8601) |
| `productId`       | `Integer`        | Identificador del producto     |
| `brandId`         | `Integer`        | Identificador de la marca      |

**Ejemplo:**

```
GET /api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1
```

**Respuesta (200 OK):**

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

Si no se encuentra un precio aplicable, se devuelve **404 Not Found**.

## Datos de ejemplo

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4          | 35455      | 1        | 38.95 | EUR  |

Cuando varias tarifas coinciden para la misma fecha, se aplica la de mayor **prioridad**.

## Tests

```bash
mvn test
```

Los tests unitarios del controlador se encuentran en:

```
src/test/java/com/example/prices/infrastructure/adapter/in/rest/PriceControllerTest.java
```

Cubren los 5 escenarios solicitados (producto 35455, marca 1) más un caso 404:

| Test | Fecha | Tarifa esperada | Precio esperado |
|------|-------|-----------------|-----------------|
| 1    | 2020-06-14 10:00 | 1 | 35.50 |
| 2    | 2020-06-14 16:00 | 2 | 25.45 |
| 3    | 2020-06-14 21:00 | 1 | 35.50 |
| 4    | 2020-06-15 10:00 | 3 | 30.50 |
| 5    | 2020-06-16 21:00 | 4 | 38.95 |
| 6    | Sin coincidencia  | — | 404   |

## Configuración

| Variable      | Valor por defecto      | Descripción             |
|---------------|------------------------|-------------------------|
| `DB_URL`      | `jdbc:h2:mem:pricesdb` | URL de la base de datos |
| `DB_DRIVER`   | `org.h2.Driver`        | Driver JDBC             |
| `DB_USERNAME` | `sa`                   | Usuario de BD           |
| `DB_PASSWORD` | *(vacío)*              | Contraseña de BD        |
