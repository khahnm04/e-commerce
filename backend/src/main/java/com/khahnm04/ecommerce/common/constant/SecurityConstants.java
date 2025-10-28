package com.khahnm04.ecommerce.common.constant;

public class SecurityConstants {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh"
    };

    public static String[] getPublicEndpoints() {
        return PUBLIC_ENDPOINTS.clone();
    }

    private SecurityConstants() {}

}
