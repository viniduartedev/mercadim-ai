package com.mercadimai.stock.dto;

import com.mercadimai.stock.enums.StockMovementType;
import java.time.OffsetDateTime;

public record StockMovementResponse(
        Long id,
        Long productId,
        String productName,
        StockMovementType type,
        Integer quantity,
        String reason,
        String referenceCode,
        OffsetDateTime createdAt
) {
}
