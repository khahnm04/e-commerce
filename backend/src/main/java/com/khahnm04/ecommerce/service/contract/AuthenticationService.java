package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.*;
import com.khahnm04.ecommerce.dto.response.IntrospectResponse;
import com.khahnm04.ecommerce.dto.response.LoginResponse;
import com.khahnm04.ecommerce.dto.response.RefreshResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    LoginResponse login(LoginRequest request);
    void logout(String authHeader, LogoutRequest request) throws ParseException, JOSEException;
    RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

}
