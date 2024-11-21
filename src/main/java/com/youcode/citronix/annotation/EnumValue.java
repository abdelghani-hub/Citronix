package com.youcode.citronix.annotation;

import java.lang.annotation.*;

import com.youcode.citronix.validator.EnumValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EnumValueValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {
    Class<? extends Enum<?>> enumClass();
    String message() default "Value is not in the list of allowed values";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
