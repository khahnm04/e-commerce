package com.khahnm04.ecommerce.controller;

import com.khahnm04.ecommerce.dto.request.*;
import com.khahnm04.ecommerce.dto.response.*;
import com.khahnm04.ecommerce.service.contract.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(
        @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        return ApiResponse.<LoginResponse>builder()
                .data(authenticationService.login(request, response))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(
        @RequestBody IntrospectRequest request
    ) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .data(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody LogoutRequest request
    ) throws ParseException, JOSEException {
        authenticationService.logout(authHeader, request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<RefreshResponse> authenticate(
        @RequestBody RefreshRequest request
    ) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<RefreshResponse>builder()
                .data(result)
                .build();
    }

}
