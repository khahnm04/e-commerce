package com.khahnm04.fashionecommerce.service;

import com.khahnm04.fashionecommerce.dto.request.AuthenticationRequest;
import com.khahnm04.fashionecommerce.dto.request.IntrospectRequest;
import com.khahnm04.fashionecommerce.dto.request.LogoutRequest;
import com.khahnm04.fashionecommerce.dto.response.AuthenticationResponse;
import com.khahnm04.fashionecommerce.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse login(AuthenticationRequest request);
    void logout(LogoutRequest request) throws ParseException, JOSEException;

}
