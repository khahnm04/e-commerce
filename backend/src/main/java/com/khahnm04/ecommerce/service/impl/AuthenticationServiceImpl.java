package com.khahnm04.ecommerce.service.impl;

import com.khahnm04.ecommerce.common.constant.TokenConstants;
import com.khahnm04.ecommerce.common.enums.GenderEnum;
import com.khahnm04.ecommerce.common.enums.RoleEnum;
import com.khahnm04.ecommerce.common.enums.StatusEnum;
import com.khahnm04.ecommerce.dto.request.*;
import com.khahnm04.ecommerce.dto.response.*;
import com.khahnm04.ecommerce.entity.RedisToken;
import com.khahnm04.ecommerce.entity.Role;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.RedisTokenRepository;
import com.khahnm04.ecommerce.repository.RoleRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.contract.AuthenticationService;
import com.khahnm04.ecommerce.service.contract.JwtService;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Cookie;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final RedisTokenRepository redisTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

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

        addAccessTokenCookies(response, accessToken.getToken());
        addRefreshTokenCookies(response, refreshToken.getToken());

        return LoginResponse.builder()
                .userId(user.getId())
                .build();
    }

    @Override
    public void logout(String accessToken, String refreshToken, HttpServletResponse response) {
        try {
            // save access token
            var signAccessToken = jwtService.verifyAccessToken(accessToken);

            User user = userRepository.findByIdentifier(signAccessToken.getJWTClaimsSet().getSubject())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);

            redisTokenRepository.save(RedisToken.builder()
                    .jwtId(signAccessToken.getJWTClaimsSet().getJWTID())
                    .expiredTime(signAccessToken.getJWTClaimsSet().getExpirationTime().getTime())
                    .build());
            log.info("save access token success");

            // remove refresh token
            var signRefreshToken = jwtService.verifyRefreshToken(refreshToken);
            redisTokenRepository.deleteById(signRefreshToken.getJWTClaimsSet().getJWTID());
            log.info("remove refresh token success");

            // delete token from cookie
            clearAccessTokenCookie(response);
            clearRefreshTokenCookie(response);

        } catch (AppException | ParseException e) {
            log.error("Token already expired");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public void refreshToken(String token, HttpServletResponse response) {
        try {
            SignedJWT signedJWT = jwtService.verifyRefreshToken(token);

            String subject = signedJWT.getJWTClaimsSet().getSubject();
            User user = userRepository.findByIdentifier(subject)
                    .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

            TokenPayload accessToken = jwtService.generateAccessToken(user);
            addAccessTokenCookies(response, accessToken.getToken());
        } catch (ParseException e) {
            log.error("cannot refresh token");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (!StringUtils.hasText(request.getEmail()) && !StringUtils.hasText(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.EMAIL_OR_PHONE_NUMBER_REQUIRED);
        }

        User newUser = userMapper.fromRegisterRequestToUser(request);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        Set<Role> roles = new HashSet<>();
        roleRepository.findById(RoleEnum.USER.name()).ifPresent(roles::add);
        newUser.setRoles(roles);
        newUser.setGender(GenderEnum.UNKNOWN);
        newUser.setStatus(StatusEnum.ACTIVE);

        try {
            newUser = userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return RegisterResponse.builder()
                .userId(newUser.getId())
                .build();
    }

    private void addAccessTokenCookies(HttpServletResponse response, String accessToken) {
        ResponseCookie accessCookie = ResponseCookie.from(TokenConstants.ACCESS_TOKEN, accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofMinutes(15))
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    }

    private void addRefreshTokenCookies(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from(TokenConstants.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/v1/auth")
                .maxAge(Duration.ofDays(30))
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    public void clearAccessTokenCookie(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(TokenConstants.ACCESS_TOKEN, "")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie refreshTokenCookie = ResponseCookie.from(TokenConstants.REFRESH_TOKEN, "")
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

}
