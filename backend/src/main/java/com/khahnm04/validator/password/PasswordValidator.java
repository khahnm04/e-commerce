package com.khahnm04.validator.password;

import com.khahnm04.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        System.out.println(password);
        if (password == null || password.isEmpty()) {
            message(ErrorCode.PASSWORD_REQUIRED, context);
            return false;
        }
        if (password.length() < 8) {
            message(ErrorCode.PASSWORD_TOO_SHORT, context);
            return false;
        }
        if (password.chars().noneMatch(Character::isUpperCase)) {
            message(ErrorCode.PASSWORD_MISSING_UPPERCASE, context);
            return false;
        }
        if (password.chars().noneMatch(Character::isLowerCase)) {
            message(ErrorCode.PASSWORD_MISSING_LOWERCASE, context);
            return false;
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            message(ErrorCode.PASSWORD_MISSING_NUMBER, context);
            return false;
        }
        if (!password.matches(".*[^A-Za-z0-9].*")) {
            message(ErrorCode.PASSWORD_MISSING_SPECIAL_CHAR, context);
            return false;
        }
        return true;
    }

    private void message(ErrorCode errorCode, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode.name())
                .addConstraintViolation();
    }

}
