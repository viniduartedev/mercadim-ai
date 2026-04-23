package com.mercadimai.product.dto;

import jakarta.validation.constraints.NotNull;

public record ProductStatusRequest(
        @NotNull(message = "Status ativo é obrigatório")
        Boolean active
) {
}
