package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.common.constant.TokenConstants;
import com.khahnm04.ecommerce.dto.request.*;
import com.khahnm04.ecommerce.dto.response.*;
import com.khahnm04.ecommerce.service.contract.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        return ApiResponse.<LoginResponse>builder()
                .data(authenticationService.login(request, response))
                .message("Login Successful")
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(
        @CookieValue(name = TokenConstants.ACCESS_TOKEN) String accessToken,
        @CookieValue(name = TokenConstants.REFRESH_TOKEN) String refreshToken,
        HttpServletResponse response
    ) {
        authenticationService.logout(accessToken, refreshToken, response);
        return ApiResponse.<Void>builder()
                .message("Logout Successful")
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<Void> refresh(
        @CookieValue(name = TokenConstants.REFRESH_TOKEN) String token,
        HttpServletResponse response
    ) {
        authenticationService.refreshToken(token, response);
        return ApiResponse.<Void>builder()
                .message("refresh token Successful")
                .build();
    }

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(
        @Valid @RequestBody RegisterRequest request
    ) {
        return ApiResponse.<RegisterResponse>builder()
                .data(authenticationService.register(request))
                .message("register Successful")
                .build();
    }

}
