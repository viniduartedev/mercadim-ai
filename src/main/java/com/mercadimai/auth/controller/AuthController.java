package com.mercadimai.auth.controller;

import com.mercadimai.auth.dto.LoginRequest;
import com.mercadimai.auth.dto.LoginResponse;
import com.mercadimai.auth.service.AuthService;
import com.mercadimai.shared.api.ApiSuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiSuccessResponse.of("Login realizado com sucesso", authService.login(request)));
    }
}
