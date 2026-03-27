package com.mercadimai.shared.api;

import java.time.OffsetDateTime;

public record ApiSuccessResponse<T>(
        String message,
        T data,
        OffsetDateTime timestamp
) {
    public static <T> ApiSuccessResponse<T> of(String message, T data) {
        return new ApiSuccessResponse<>(message, data, OffsetDateTime.now());
    }
}
