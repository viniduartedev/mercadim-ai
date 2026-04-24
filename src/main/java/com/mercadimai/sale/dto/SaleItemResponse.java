package com.mercadimai.sale.dto;

import java.math.BigDecimal;

public record SaleItemResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
