package com.mercadimai.userprofile.controller;

import com.mercadimai.exception.UnauthorizedException;
import com.mercadimai.security.principal.AuthenticatedUserProfile;
import com.mercadimai.shared.api.ApiSuccessResponse;
import com.mercadimai.shared.api.PageResponse;
import com.mercadimai.userprofile.dto.UserProfileResponse;
import com.mercadimai.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PageResponse<UserProfileResponse>>> list(
            @RequestParam(required = false) Boolean active,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserProfileResponse> page = userProfileService.list(active, pageable);
        return ResponseEntity.ok(ApiSuccessResponse.of("Perfis carregados com sucesso", PageResponse.from(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<UserProfileResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiSuccessResponse.of("Perfil carregado com sucesso", userProfileService.getById(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResponse<UserProfileResponse>> me(
            @AuthenticationPrincipal AuthenticatedUserProfile authenticatedUser
    ) {
        if (authenticatedUser == null) {
            throw new UnauthorizedException("Usuário não autenticado");
        }

        UserProfileResponse response = userProfileService.getMyProfile(authenticatedUser.authUserId());
        return ResponseEntity.ok(ApiSuccessResponse.of("Perfil carregado com sucesso", response));
    }
}
