package com.youcode.citronix.validator;

import com.youcode.citronix.annotation.EnumValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValue annotation) {
        this.enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(enumValues)
                .anyMatch(enumValue -> enumValue.name().equals(value));
    }
}

