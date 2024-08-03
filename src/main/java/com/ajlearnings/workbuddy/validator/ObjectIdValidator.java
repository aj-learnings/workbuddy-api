package com.ajlearnings.workbuddy.validator;

import com.ajlearnings.workbuddy.annotation.ValidObjectId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.bson.types.ObjectId;

public class ObjectIdValidator implements ConstraintValidator<ValidObjectId, String> {

    @Override
    public void initialize(ValidObjectId constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // or true, based on whether null is considered valid
        }
        boolean isValid = ObjectId.isValid(value);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Invalid id")
                .addConstraintViolation();
        return isValid;
    }
}

