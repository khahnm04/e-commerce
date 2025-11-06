package com.khahnm04.ecommerce.service.auth.strategy;

import com.khahnm04.ecommerce.dto.request.LoginRequest;
import com.khahnm04.ecommerce.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameLoginStrategy implements LoginStrategy {

    private final AuthenticationManager authenticationManager;

    @Override
    public boolean supports(LoginRequest request) {
        return StringUtils.hasText(request.getUsername());
    }

    @Override
    public User authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return (User) authentication.getPrincipal();
    }

}
