package com.khahnm04.ecommerce.util;

import com.khahnm04.ecommerce.exception.ErrorCode;
import jakarta.validation.ConstraintValidatorContext;

public class ValidationUtils {

    public static void buildError(ErrorCode errorCode, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode.name())
                .addConstraintViolation();
    }

}
