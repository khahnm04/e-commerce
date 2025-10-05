package com.khahnm04.validation.email;

import com.khahnm04.exception.ErrorCode;
import com.khahnm04.util.ValidationUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (!email.matches(EMAIL_REGEX)) {
            ValidationUtil.buildError(ErrorCode.EMAIL_INVALID_FORMAT, context);
            return false;
        }
        return true;
    }

}
