package com.mercadimai.shared.api;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String path,
        OffsetDateTime timestamp,
        List<FieldValidationError> fieldErrors
) {
    public record FieldValidationError(String field, String message) {
    }
}
