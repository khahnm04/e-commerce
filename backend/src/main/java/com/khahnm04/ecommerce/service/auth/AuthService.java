package com.khahnm04.ecommerce.service.auth;

import com.khahnm04.ecommerce.dto.request.auth.LoginRequest;
import com.khahnm04.ecommerce.dto.request.auth.RegisterRequest;
import com.khahnm04.ecommerce.dto.response.auth.LoginResponse;
import com.khahnm04.ecommerce.dto.response.auth.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request, HttpServletResponse response);
    void logout(String accessToken, String refreshToken, HttpServletResponse response);
    void refreshToken(String token, HttpServletResponse response);
    RegisterResponse register(RegisterRequest request);

}
