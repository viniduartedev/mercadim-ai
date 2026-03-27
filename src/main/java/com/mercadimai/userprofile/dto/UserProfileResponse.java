package com.mercadimai.userprofile.dto;

import com.mercadimai.userprofile.enums.UserRole;
import java.time.OffsetDateTime;

public record UserProfileResponse(
        Long id,
        String authUserId,
        String name,
        String email,
        UserRole role,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
