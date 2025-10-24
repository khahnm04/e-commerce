package com.khahnm04.ecommerce.config;

import com.khahnm04.ecommerce.common.constant.SecurityConstants;
import com.khahnm04.ecommerce.common.constant.TokenConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

@Component
public class CookieBearerTokenResolver implements BearerTokenResolver {

    @Override
    public String resolve(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Skip public endpoints
        for (String endpoint : SecurityConstants.getPublicEndpoints()) {
            if (path.equals(endpoint)) {
                return null;
            }
        }

        // Đọc token từ cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TokenConstants.ACCESS_TOKEN.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

}
