package com.khahnm04.ecommerce.modules.auth.application.service;

import com.khahnm04.ecommerce.modules.auth.application.request.LoginRequest;
import com.khahnm04.ecommerce.modules.auth.application.request.RegisterRequest;
import com.khahnm04.ecommerce.modules.auth.application.response.LoginResponse;
import com.khahnm04.ecommerce.modules.auth.application.response.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request, HttpServletResponse response);
    void logout(String accessToken, String refreshToken, HttpServletResponse response);
    void refreshToken(String token, HttpServletResponse response);
    RegisterResponse register(RegisterRequest request);

}
