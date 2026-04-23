package com.mercadimai.category.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryStatusRequest(
        @NotNull(message = "Status ativo é obrigatório")
        Boolean active
) {
}
