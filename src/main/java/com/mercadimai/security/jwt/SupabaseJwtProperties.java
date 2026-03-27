package com.mercadimai.security.jwt;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "supabase.jwt")
public record SupabaseJwtProperties(
        @NotBlank String issuer,
        @NotBlank String audience,
        @NotBlank String jwksUrl
) {
}
