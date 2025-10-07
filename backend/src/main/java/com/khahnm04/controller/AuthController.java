package com.khahnm04.controller;

import com.khahnm04.dto.request.AuthRequest;
import com.khahnm04.dto.request.IntrospectRequest;
import com.khahnm04.dto.response.ApiResponse;
import com.khahnm04.dto.response.AuthResponse;
import com.khahnm04.dto.response.IntrospectResponse;
import com.khahnm04.service.auth.IAuthService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> login(
        @RequestBody AuthRequest request
    ) {
        return ApiResponse.<AuthResponse>builder()
                .data(authService.login(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(
        @RequestBody IntrospectRequest request
    ) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .data(authService.introspect(request))
                .build();
    }

}
