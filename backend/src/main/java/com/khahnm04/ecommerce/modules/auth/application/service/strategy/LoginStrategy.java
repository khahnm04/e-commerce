package com.khahnm04.ecommerce.modules.auth.application.service.strategy;

import com.khahnm04.ecommerce.modules.auth.application.request.LoginRequest;
import com.khahnm04.ecommerce.modules.user.domain.entity.User;

public interface LoginStrategy {

    boolean supports(LoginRequest request);
    User authenticate(LoginRequest request);

}
