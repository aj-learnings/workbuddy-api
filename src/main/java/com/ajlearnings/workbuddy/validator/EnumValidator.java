package com.ajlearnings.workbuddy.validator;

import com.ajlearnings.workbuddy.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // handle @NotNull separately
        }
        boolean isValid = Arrays.stream(enumClass.getEnumConstants())
                                .anyMatch(e -> e.name().equals(value));

        if (!isValid) {
            List<String> enumValues = Arrays.stream(enumClass.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            String enumValuesString = String.join(", ", enumValues);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid value. Allowed values are: " + enumValuesString)
                    .addConstraintViolation();
        }

        return isValid;
    }
}

