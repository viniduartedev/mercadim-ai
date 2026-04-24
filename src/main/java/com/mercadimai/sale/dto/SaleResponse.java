package com.mercadimai.sale.dto;

import com.mercadimai.sale.enums.SaleStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record SaleResponse(
        Long id,
        SaleStatus status,
        BigDecimal total,
        String notes,
        OffsetDateTime soldAt,
        Long userProfileId,
        String userProfileName,
        List<SaleItemResponse> items,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
