package com.khahnm04.ecommerce.util;

import com.khahnm04.ecommerce.exception.ErrorCode;
import jakarta.validation.ConstraintValidatorContext;

public class ValidationUtil {

    public static void buildError(ErrorCode errorCode, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorCode.name())
                .addConstraintViolation();
    }

}
