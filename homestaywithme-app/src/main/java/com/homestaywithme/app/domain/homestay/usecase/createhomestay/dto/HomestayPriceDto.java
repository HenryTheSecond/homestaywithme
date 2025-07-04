package com.homestaywithme.app.domain.homestay.usecase.createhomestay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomestayPriceDto {
    private Long homestayId;
    private LocalDate date;
    private BigDecimal price;
}
