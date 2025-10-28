package com.khahnm04.ecommerce.util;

import com.khahnm04.ecommerce.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {

    private SecurityUtils() {}

    /**
     * Lấy ID của user hiện tại từ SecurityContext
     * @return User ID hoặc null nếu không có user đăng nhập
     */
    public static Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                return ((User) principal).getId();
            }

        } catch (Exception e) {
            log.error("Error getting current user ID", e);
        }

        return null;
    }

    /**
     * Lấy User object hiện tại từ SecurityContext
     * @return User object hoặc null
     */
    public static User getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            if (authentication != null &&
                    authentication.getPrincipal() instanceof User) {
                return (User) authentication.getPrincipal();
            }

        } catch (Exception e) {
            log.error("Error getting current user", e);
        }

        return null;
    }

    /**
     * Kiểm tra user hiện tại có authenticated không
     * @return true nếu đã đăng nhập
     */
    public static boolean isAuthenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            return authentication != null &&
                    authentication.isAuthenticated() &&
                    !(authentication instanceof AnonymousAuthenticationToken);

        } catch (Exception e) {
            log.error("Error checking authentication", e);
        }
        return false;
    }

}
