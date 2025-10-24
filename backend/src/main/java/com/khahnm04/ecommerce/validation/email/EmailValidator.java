package com.khahnm04.ecommerce.validation.email;

import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.util.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (StringUtils.hasText(email) && !email.matches(EMAIL_REGEX)) {
            ValidationUtils.buildError(ErrorCode.EMAIL_INVALID_FORMAT, context);
            return false;
        }
        return true;
    }

}
