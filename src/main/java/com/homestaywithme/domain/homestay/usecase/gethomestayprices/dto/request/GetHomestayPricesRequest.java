package com.homestaywithme.domain.homestay.usecase.gethomestayprices.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetHomestayPricesRequest {
    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate to;
}
