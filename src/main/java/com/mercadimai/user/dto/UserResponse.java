package com.mercadimai.user.dto;

import com.mercadimai.user.enums.Role;
import java.time.OffsetDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
