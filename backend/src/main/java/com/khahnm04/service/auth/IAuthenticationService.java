package com.khahnm04.service.auth;

import com.khahnm04.dto.request.AuthenticationRequest;
import com.khahnm04.dto.request.IntrospectRequest;
import com.khahnm04.dto.response.AuthenticationResponse;
import com.khahnm04.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse login(AuthenticationRequest request);

}
