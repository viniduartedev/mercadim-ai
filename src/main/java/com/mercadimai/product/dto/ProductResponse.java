package com.mercadimai.product.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        String barcode,
        String description,
        BigDecimal salePrice,
        BigDecimal cost,
        Integer currentStock,
        Integer minimumStock,
        boolean active,
        Long categoryId,
        String categoryName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
