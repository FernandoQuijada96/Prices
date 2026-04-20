package com.example.prices.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
                SELECT p FROM PriceEntity p
                WHERE p.brandId = :brandId
                AND p.productId = :productId
                AND :applicationDate BETWEEN p.startDate AND p.endDate
            """)
    List<PriceEntity> findPricesByBrandProductAndDate(
            @Param("brandId") Integer brandId,
            @Param("productId") Integer productId,
            @Param("applicationDate") LocalDateTime applicationDate
    );
}
