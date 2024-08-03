package com.ajlearnings.workbuddy.annotation;

import com.ajlearnings.workbuddy.validator.ObjectIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ObjectIdValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidObjectId {
    String message() default "Invalid ObjectId format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

