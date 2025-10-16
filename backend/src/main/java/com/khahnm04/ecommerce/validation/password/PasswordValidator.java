package com.khahnm04.ecommerce.validation.password;

import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.util.ValidationUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecialChar;

    @Override
    public void initialize(ValidPassword annotation) {
        this.minLength = annotation.minLength();
        this.requireUppercase = annotation.requireUppercase();
        this.requireLowercase = annotation.requireLowercase();
        this.requireDigit = annotation.requireDigit();
        this.requireSpecialChar = annotation.requireSpecialChar();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        // Null/empty check
        if (password == null || password.isEmpty()) {
            ValidationUtil.buildError(ErrorCode.PASSWORD_REQUIRED, context);
            return false;
        }

        // Length checks
        if (password.length() < minLength) {
            ValidationUtil.buildError(ErrorCode.PASSWORD_TOO_SHORT, context);
            return false;
        }

        // Uppercase check
        if (requireUppercase && password.chars().noneMatch(Character::isUpperCase)) {
            ValidationUtil.buildError(ErrorCode.PASSWORD_MISSING_UPPERCASE, context);
            return false;
        }

        // Lowercase check
        if (requireLowercase && password.chars().noneMatch(Character::isLowerCase)) {
            ValidationUtil.buildError(ErrorCode.PASSWORD_MISSING_LOWERCASE, context);
            return false;
        }

        // Digit check
        if (requireDigit && password.chars().noneMatch(Character::isDigit)) {
            ValidationUtil.buildError(ErrorCode.PASSWORD_MISSING_NUMBER, context);
            return false;
        }

        // Special character check
        if (requireSpecialChar && !password.matches(".*[^A-Za-z0-9].*")) {
            ValidationUtil.buildError(ErrorCode.PASSWORD_MISSING_SPECIAL_CHAR, context);
            return false;
        }

        return true;
    }

}
