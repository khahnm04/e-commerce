package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.response.TokenPayload;
import com.khahnm04.ecommerce.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;

public interface JwtService {

    TokenPayload generateAccessToken(User user);
    TokenPayload generateRefreshToken(User user);
    SignedJWT verifyAccessToken(String token) throws JOSEException, ParseException;
    SignedJWT verifyRefreshToken(String token) throws JOSEException, ParseException;

}
