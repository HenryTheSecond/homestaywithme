package com.homestaywithme.domain.homestay.usecase.updatehomestayprice.validator.updatehomestaypricerequest;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FromDateLessThanOrEqualToDateValidator.class)
public @interface FromDateLessThanOrEqualToDate {
    String message() default "From date must be less than or equal to to date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
