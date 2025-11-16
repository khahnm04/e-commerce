package com.khahnm04.ecommerce.service.auth.strategy;

import com.khahnm04.ecommerce.dto.request.auth.LoginRequest;
import com.khahnm04.ecommerce.entity.user.User;

public interface LoginStrategy {

    boolean supports(LoginRequest request);
    User authenticate(LoginRequest request);

}
