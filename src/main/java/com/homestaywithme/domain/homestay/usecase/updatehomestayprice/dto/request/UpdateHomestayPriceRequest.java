package com.homestaywithme.domain.homestay.usecase.updatehomestayprice.dto.request;

import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.validator.updatehomestaypricerequest.FromDateLessThanOrEqualToDate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FromDateLessThanOrEqualToDate
public class UpdateHomestayPriceRequest {
    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;

    @NotNull
    private BigDecimal price;
}
