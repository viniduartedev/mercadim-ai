package com.mercadimai.category.dto;

import java.time.OffsetDateTime;

public record CategoryResponse(
        Long id,
        String name,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
