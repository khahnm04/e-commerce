package com.khahnm04.ecommerce.exception;

import com.khahnm04.ecommerce.dto.response.FieldErrorResponse;

import java.util.List;

public class BusinessValidationException extends RuntimeException {

    private final List<FieldErrorResponse> errors;

    public BusinessValidationException(List<FieldErrorResponse> errors) {
        super("Validation error");
        this.errors = errors;
    }

    public List<FieldErrorResponse> getErrors() {
        return errors;
    }

}
