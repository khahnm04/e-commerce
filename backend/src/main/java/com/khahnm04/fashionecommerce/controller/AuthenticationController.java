package com.khahnm04.fashionecommerce.controller;

import com.khahnm04.fashionecommerce.dto.request.AuthenticationRequest;
import com.khahnm04.fashionecommerce.dto.request.IntrospectRequest;
import com.khahnm04.fashionecommerce.dto.request.LogoutRequest;
import com.khahnm04.fashionecommerce.dto.response.ApiResponse;
import com.khahnm04.fashionecommerce.dto.response.AuthenticationResponse;
import com.khahnm04.fashionecommerce.dto.response.IntrospectResponse;
import com.khahnm04.fashionecommerce.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest request
    ) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authenticationService.login(request))
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
        @RequestBody LogoutRequest request
    ) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

}
