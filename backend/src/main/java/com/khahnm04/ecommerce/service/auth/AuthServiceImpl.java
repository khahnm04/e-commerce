package com.khahnm04.ecommerce.service.auth;

import com.khahnm04.ecommerce.common.constant.TokenConstants;
import com.khahnm04.ecommerce.common.enums.RoleEnum;
import com.khahnm04.ecommerce.dto.request.auth.LoginRequest;
import com.khahnm04.ecommerce.dto.request.auth.RegisterRequest;
import com.khahnm04.ecommerce.dto.response.auth.LoginResponse;
import com.khahnm04.ecommerce.dto.response.auth.RegisterResponse;
import com.khahnm04.ecommerce.dto.response.auth.TokenPayload;
import com.khahnm04.ecommerce.entity.RedisToken;
import com.khahnm04.ecommerce.entity.Role;
import com.khahnm04.ecommerce.entity.User;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.UserMapper;
import com.khahnm04.ecommerce.repository.RedisTokenRepository;
import com.khahnm04.ecommerce.repository.RoleRepository;
import com.khahnm04.ecommerce.repository.UserRepository;
import com.khahnm04.ecommerce.service.auth.jwt.JwtService;
import com.khahnm04.ecommerce.service.auth.strategy.LoginStrategy;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RedisTokenRepository redisTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final List<LoginStrategy> loginStrategies;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Map<Function<RegisterRequest, Boolean>, ErrorCode> validators = Map.of(
                r -> !StringUtils.hasText(r.getPhoneNumber()), ErrorCode.PHONE_NUMBER_REQUIRED,
                r -> userRepository.existsByPhoneNumber(r.getPhoneNumber()), ErrorCode.PHONE_NUMBER_EXISTED,
                r -> userRepository.existsByEmail(r.getEmail()), ErrorCode.EMAIL_EXISTED,
                r -> !request.getPassword().equals(request.getConfirmPassword()), ErrorCode.PASSWORD_CONFIRMATION_MISMATCH
        );

        validators.forEach((predicate, error) -> {
            if (predicate.apply(request)) {
                throw new AppException(error);
            }
        });

        User newUser = userMapper.fromRegisterRequestToUser(request);

        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(RoleEnum.USER.name()).ifPresent(roles::add);
        newUser.setRoles(roles);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser = userRepository.save(newUser);
        return RegisterResponse.builder()
                .userId(newUser.getId())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        LoginStrategy loginStrategy = loginStrategies.stream()
                .filter(s -> s.supports(request))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.LOGIN_IDENTIFIER_REQUIRED));

        User user = loginStrategy.authenticate(request);

        TokenPayload accessToken = jwtService.generateAccessToken(user);
        TokenPayload refreshToken = jwtService.generateRefreshToken(user);

        redisTokenRepository.save(RedisToken.builder()
                    .jwtId(refreshToken.getJwtId())
                    .expiredTime(refreshToken.getExpiredTime().getTime())
                    .build());

        addAccessTokenCookies(response, accessToken.getToken());
        addRefreshTokenCookies(response, refreshToken.getToken());

        log.info("User with id = {} has been authenticated", user.getId());
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

            User user = userRepository.findByIdentifier(signedJWT.getJWTClaimsSet().getSubject())
                    .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

            TokenPayload accessToken = jwtService.generateAccessToken(user);
            addAccessTokenCookies(response, accessToken.getToken());
        } catch (ParseException e) {
            log.error("cannot refresh token");
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private void addAccessTokenCookies(HttpServletResponse response, String accessToken) {
        ResponseCookie accessCookie = ResponseCookie.from(TokenConstants.ACCESS_TOKEN, accessToken)
                .httpOnly(true)
                .secure(false)
                .domain("localhost")
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
    }

    private void addRefreshTokenCookies(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from(TokenConstants.REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(false)
                .domain("localhost")
                .path("/api/v1/auth")
                .maxAge(Duration.ofDays(30))
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    public void clearAccessTokenCookie(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(TokenConstants.ACCESS_TOKEN, "")
                .maxAge(0)
                .domain("localhost")
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
                .domain("localhost")
                .path("/api/v1/auth")
                .httpOnly(true)
                .secure(false)
                .sameSite(Cookie.SameSite.STRICT.attributeValue())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

}
