package com.mercadimai.auth.controller;

import com.mercadimai.auth.dto.LoginRequest;
import com.mercadimai.auth.dto.LoginResponse;
import com.mercadimai.shared.api.ApiSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                ApiSuccessResponse.of(
                        "Login local desabilitado temporariamente. Use Supabase Auth no frontend.",
                        null
                )
        );
    }
}
