package com.khahnm04.ecommerce.service.contract;

import com.khahnm04.ecommerce.dto.request.*;
import com.khahnm04.ecommerce.dto.response.LoginResponse;
import com.khahnm04.ecommerce.dto.response.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request, HttpServletResponse response);
    void logout(String accessToken, String refreshToken, HttpServletResponse response);
    void refreshToken(String token, HttpServletResponse response);
    RegisterResponse register(RegisterRequest request);

}
