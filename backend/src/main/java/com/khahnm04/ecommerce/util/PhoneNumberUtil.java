package com.khahnm04.ecommerce.util;

public class PhoneNumberUtil {

    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }
        String normalized = phoneNumber.replaceAll(" ", "");
        if (normalized.startsWith("+84")) {
            normalized = normalized.replaceFirst("\\+84", "0");
        }
        return normalized;
    }

}
