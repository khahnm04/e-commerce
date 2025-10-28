package com.khahnm04.ecommerce.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Slf4j
@Component
public class AuditorAwareConfig implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of(0L);
            }
            Object principal = authentication.getPrincipal();
            if (principal instanceof Jwt jwt) {
                Long userId = jwt.getClaim("id");
                if (userId == null) {
                    userId = jwt.getClaim("userId");
                }
                if (userId == null) {
                    userId = jwt.getClaim("user_id");
                }
                return Optional.of(userId != null ? userId : 0L);
            }
            return Optional.of(0L);
        } catch (Exception e) {
            return Optional.of(0L);
        }
    }

}
