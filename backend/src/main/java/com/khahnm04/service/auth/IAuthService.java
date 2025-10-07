package com.khahnm04.service.auth;

import com.khahnm04.dto.request.AuthRequest;
import com.khahnm04.dto.request.IntrospectRequest;
import com.khahnm04.dto.response.AuthResponse;
import com.khahnm04.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthService {

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthResponse login(AuthRequest request);

}
