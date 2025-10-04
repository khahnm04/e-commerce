package com.khahnm04.validator.email;

import com.khahnm04.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            message(ErrorCode.EMAIL_REQUIRED, context);
            return false;
        }
        if (!email.matches(EMAIL_REGEX)) {
            message(ErrorCode.EMAIL_INVALID_FORMAT, context);
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
