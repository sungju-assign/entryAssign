package com.sungjujjang.entryAssgin.domain.auth.controller;

import com.sungjujjang.entryAssgin.domain.auth.dto.LoginRequest;
import com.sungjujjang.entryAssgin.domain.auth.dto.LoginResponse;
import com.sungjujjang.entryAssgin.domain.auth.dto.MeResponse;
import com.sungjujjang.entryAssgin.domain.auth.dto.RegisterRequest;
import com.sungjujjang.entryAssgin.domain.auth.service.AuthService;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Void> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        authService.register(registerRequest);
        return ApiResponse.ok();
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ApiResponse.ok(loginResponse);
    }

    @GetMapping("/me")
    public ApiResponse<MeResponse> me(
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        MeResponse meResponse = authService.me(userId);
        return ApiResponse.ok(meResponse);
    }
}
