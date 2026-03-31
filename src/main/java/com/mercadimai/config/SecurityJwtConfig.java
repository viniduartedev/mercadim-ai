package com.mercadimai.config;

import com.mercadimai.security.jwt.SupabaseJwtProperties;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableConfigurationProperties(SupabaseJwtProperties.class)
public class SecurityJwtConfig {

    @Bean
    public JwtDecoder jwtDecoder(SupabaseJwtProperties properties) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(properties.jwksUrl()).build();

        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(properties.issuer());
        OAuth2TokenValidator<Jwt> audienceValidator = token -> {
            List<String> audiences = token.getAudience();
            if (audiences != null && audiences.contains(properties.audience())) {
                return OAuth2TokenValidatorResult.success();
            }

            OAuth2Error error = new OAuth2Error(
                    "invalid_token",
                    "Token sem audience esperado",
                    null
            );
            return OAuth2TokenValidatorResult.failure(error);
        };

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator));
        return decoder;
    }
}
