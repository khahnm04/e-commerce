package com.khahnm04.ecommerce.config;

import com.khahnm04.ecommerce.common.constant.TokenConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class CookieBearerTokenResolver implements BearerTokenResolver {

    @Override
    public String resolve(HttpServletRequest request) {
        Cookie tokenCookie = WebUtils.getCookie(request, TokenConstants.ACCESS_TOKEN);
        return tokenCookie != null ? tokenCookie.getValue() : null;
    }

}
