package com.mercadimai.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 180, message = "Nome deve ter no máximo 180 caracteres")
        String name,

        @NotBlank(message = "SKU é obrigatório")
        @Size(max = 80, message = "SKU deve ter no máximo 80 caracteres")
        String sku,

        @Size(max = 80, message = "Código de barras deve ter no máximo 80 caracteres")
        String barcode,

        String description,

        @NotNull(message = "Preço de venda é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "Preço de venda não pode ser negativo")
        BigDecimal salePrice,

        @NotNull(message = "Custo é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "Custo não pode ser negativo")
        BigDecimal cost,

        @NotNull(message = "Estoque atual é obrigatório")
        @Min(value = 0, message = "Estoque atual não pode ser negativo")
        Integer currentStock,

        @NotNull(message = "Estoque mínimo é obrigatório")
        @Min(value = 0, message = "Estoque mínimo não pode ser negativo")
        Integer minimumStock,

        @NotNull(message = "Categoria é obrigatória")
        Long categoryId,

        Boolean active
) {
}
