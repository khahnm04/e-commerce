package com.khahnm04.controller;

import com.khahnm04.dto.request.AuthenticationRequest;
import com.khahnm04.dto.request.IntrospectRequest;
import com.khahnm04.dto.response.ApiResponse;
import com.khahnm04.dto.response.AuthenticationResponse;
import com.khahnm04.dto.response.IntrospectResponse;
import com.khahnm04.service.auth.IAuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

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

}
