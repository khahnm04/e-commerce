package com.khahnm04.fashionecommerce.controller;

import com.khahnm04.fashionecommerce.dto.request.AuthenticationRequest;
import com.khahnm04.fashionecommerce.dto.request.IntrospectRequest;
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

    private final AuthenticationService authService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(
        @RequestBody AuthenticationRequest request
    ) {
        return ApiResponse.<AuthenticationResponse>builder()
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
