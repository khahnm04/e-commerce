package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.dto.request.*;
import com.khahnm04.ecommerce.dto.response.*;
import com.khahnm04.ecommerce.entity.RedisToken;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.RedisTokenRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.contract.AuthenticationService;
import com.khahnm04.ecommerce.service.contract.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final RedisTokenRepository redisTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            jwtService.verifyAccessToken(token);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword()));

        User user = (User) authentication.getPrincipal();

        TokenPayload accessToken = jwtService.generateAccessToken(user);
        TokenPayload refreshToken = jwtService.generateRefreshToken(user);

        redisTokenRepository.save(RedisToken.builder()
                    .jwtId(refreshToken.getJwtId())
                    .expiredTime(refreshToken.getExpiredTime().getTime())
                    .build());

        addTokenCookies(response, accessToken.getToken(), refreshToken.getToken());

        return LoginResponse.builder()
                .accessToken(accessToken.getToken())
                .user(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    public void logout(String authHeader, LogoutRequest request) throws ParseException, JOSEException {
        try {
            // save access token
            var signAccessToken = jwtService.verifyAccessToken(authHeader.replace("Bearer ", ""));
            redisTokenRepository.save(RedisToken.builder()
                    .jwtId(signAccessToken.getJWTClaimsSet().getJWTID())
                    .expiredTime(signAccessToken.getJWTClaimsSet().getExpirationTime().getTime())
                    .build());
            log.info("save access token success");

            // remove refresh token
            var signRefreshToken = jwtService.verifyRefreshToken(request.getRefreshToken());
            redisTokenRepository.deleteById(signRefreshToken.getJWTClaimsSet().getJWTID());
            log.info("remove refresh token success");
        } catch (AppException e) {
            log.error("Token already expired");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = jwtService.verifyRefreshToken(request.getToken());

        String phoneNumber = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        TokenPayload accessToken = jwtService.generateAccessToken(user);
        return RefreshResponse.builder()
                .token(accessToken.getToken())
                .build();
    }

    private void addTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        // Access Token Cookie (15 phút)
        ResponseCookie accessCookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(false) // true nếu chỉ cho gửi qua HTTPS
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .build();

        // Refresh Token Cookie (7 ngày)
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false) // true nếu chỉ cho gửi qua HTTPS
                .path("/auth")
                .maxAge(Duration.ofDays(7))
                .build();

        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }

}
