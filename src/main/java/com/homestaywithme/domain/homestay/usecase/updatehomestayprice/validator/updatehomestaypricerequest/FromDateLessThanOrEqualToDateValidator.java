package com.homestaywithme.domain.homestay.usecase.updatehomestayprice.validator.updatehomestaypricerequest;

import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.dto.request.UpdateHomestayPriceRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FromDateLessThanOrEqualToDateValidator implements
        ConstraintValidator<FromDateLessThanOrEqualToDate, UpdateHomestayPriceRequest> {
    @Override
    public void initialize(FromDateLessThanOrEqualToDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(UpdateHomestayPriceRequest updateHomestayPriceRequest, ConstraintValidatorContext constraintValidatorContext) {
        return !updateHomestayPriceRequest.getFrom().isAfter(updateHomestayPriceRequest.getTo());
    }
}
