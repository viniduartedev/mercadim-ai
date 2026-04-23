package com.mercadimai.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String name,

        @NotBlank(message = "SKU é obrigatório")
        @Size(max = 60, message = "SKU deve ter no máximo 60 caracteres")
        String sku,

        @Size(max = 50, message = "Código de barras deve ter no máximo 50 caracteres")
        String barcode,

        @Size(max = 600, message = "Descrição deve ter no máximo 600 caracteres")
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
