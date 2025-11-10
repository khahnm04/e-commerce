package com.khahnm04.ecommerce.validation.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Set<String> acceptedValues;

    @Override
    public void initialize(ValidEnum annotation) {
        String[] anyOf = annotation.anyOf();
        if (anyOf.length > 0) {
            acceptedValues = new HashSet<>(Arrays.asList(anyOf));
        } else {
            acceptedValues = Arrays.stream(annotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value == null || acceptedValues.contains(value.name());
    }

}
