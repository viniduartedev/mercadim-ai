package com.mercadimai.security.jwt;

import com.mercadimai.exception.UserProfileNotFoundException;
import com.mercadimai.security.principal.AuthenticatedUserProfile;
import com.mercadimai.userprofile.entity.UserProfile;
import com.mercadimai.userprofile.service.UserProfileService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupabaseJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserProfileService userProfileService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String authUserId = jwt.getSubject();
        UserProfile profile = userProfileService.findActiveByAuthUserId(authUserId)
                .orElseThrow(() -> new UserProfileNotFoundException("Usuário autenticado sem perfil interno ativo"));

        AuthenticatedUserProfile principal = new AuthenticatedUserProfile(
                profile.getId(),
                profile.getAuthUserId(),
                profile.getNome(),
                profile.getEmail(),
                profile.getRole()
        );

        return new UsernamePasswordAuthenticationToken(
                principal,
                jwt.getTokenValue(),
                List.of(new SimpleGrantedAuthority("ROLE_" + profile.getRole().name()))
        );
    }
}
