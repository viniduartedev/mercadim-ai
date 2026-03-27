package com.mercadimai.auth.controller;

import com.mercadimai.shared.api.ApiSuccessResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthInfoController {

    @GetMapping("/public-info")
    public ResponseEntity<ApiSuccessResponse<Map<String, String>>> publicInfo() {
        return ResponseEntity.ok(ApiSuccessResponse.of(
                "Autenticação delegada ao Supabase Auth",
                Map.of("loginProvider", "supabase-auth", "tokenType", "JWT Bearer")
        ));
    }
}
