package com.mercadimai.userprofile.controller;

import com.mercadimai.security.principal.AuthenticatedUserProfile;
import com.mercadimai.shared.api.ApiSuccessResponse;
import com.mercadimai.userprofile.dto.UserProfileResponse;
import com.mercadimai.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<ApiSuccessResponse<UserProfileResponse>> me(
            @AuthenticationPrincipal AuthenticatedUserProfile authenticatedUser
    ) {
        UserProfileResponse response = userProfileService.getMyProfile(authenticatedUser.authUserId());
        return ResponseEntity.ok(ApiSuccessResponse.of("Perfil carregado com sucesso", response));
    }
}
