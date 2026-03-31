package com.mercadimai.auth.dto;

import com.mercadimai.user.enums.Role;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long userId,
        String name,
        String email,
        Role role
) {
}
