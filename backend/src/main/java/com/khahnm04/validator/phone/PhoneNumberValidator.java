package com.khahnm04.validator.phone;

import com.khahnm04.exception.ErrorCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final String PHONE_REGEX = "^(?:\\+84|0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if(phoneNumber == null || phoneNumber.isEmpty()) {
            message(ErrorCode.PHONE_NUMBER_REQUIRED, context);
            return false;
        }
        String normalized = phoneNumber.replaceAll(" ", "");
        if (normalized.startsWith("+84")) {
            normalized = normalized.replaceFirst("\\+84", "0");
        }
        if (normalized.length() != 10) {
            message(ErrorCode.PHONE_NUMBER_INVALID_LENGTH, context);
            return false;
        }
        if (!normalized.matches(PHONE_REGEX)) {
            message(ErrorCode.PHONE_NUMBER_INVALID_FORMAT, context);
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
