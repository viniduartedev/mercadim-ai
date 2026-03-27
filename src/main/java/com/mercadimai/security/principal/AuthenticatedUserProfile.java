package com.mercadimai.security.principal;

import com.mercadimai.userprofile.enums.UserRole;

public record AuthenticatedUserProfile(
        Long profileId,
        String authUserId,
        String name,
        String email,
        UserRole role
) {
}
