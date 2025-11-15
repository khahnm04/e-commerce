package com.khahnm04.ecommerce.modules.auth.api;

import com.khahnm04.ecommerce.shared.common.constant.TokenConstants;
import com.khahnm04.ecommerce.modules.auth.application.request.LoginRequest;
import com.khahnm04.ecommerce.modules.auth.application.request.RegisterRequest;
import com.khahnm04.ecommerce.modules.auth.application.response.LoginResponse;
import com.khahnm04.ecommerce.modules.auth.application.response.RegisterResponse;
import com.khahnm04.ecommerce.modules.auth.application.service.AuthService;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    ApiResponse<RegisterResponse> register(
        @Valid @RequestBody RegisterRequest request
    ) {
        return ApiResponse.<RegisterResponse>builder()
                .data(authService.register(request))
                .message("register successful")
                .build();
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(
        @Valid @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        return ApiResponse.<LoginResponse>builder()
                .data(authService.login(request, response))
                .message("login Successful")
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(
        @CookieValue(name = TokenConstants.ACCESS_TOKEN) String accessToken,
        @CookieValue(name = TokenConstants.REFRESH_TOKEN) String refreshToken,
        HttpServletResponse response
    ) {
        authService.logout(accessToken, refreshToken, response);
        return ApiResponse.<Void>builder()
                .message("Logout Successful")
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<Void> refresh(
        @CookieValue(name = TokenConstants.REFRESH_TOKEN) String token,
        HttpServletResponse response
    ) {
        authService.refreshToken(token, response);
        return ApiResponse.<Void>builder()
                .message("refresh token Successful")
                .build();
    }

}
