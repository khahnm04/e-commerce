package com.khahnm04.ecommerce.service.auth.jwt;

import com.khahnm04.ecommerce.dto.response.auth.TokenPayload;
import com.khahnm04.ecommerce.entity.user.User;
import com.nimbusds.jwt.SignedJWT;

public interface JwtService {

    TokenPayload generateAccessToken(User user);
    TokenPayload generateRefreshToken(User user);
    SignedJWT verifyAccessToken(String token);
    SignedJWT verifyRefreshToken(String token);

}
