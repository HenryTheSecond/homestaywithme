package com.homestaywithme.app.domain.booking.repository.homestayavailability.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class SetPriceHomestayAvailability {
    private Long homestayId;
    private BigDecimal price;
    private LocalDate latestDate;

    public SetPriceHomestayAvailability(Long homestayId, BigDecimal price, LocalDate latestDate) {
        this.homestayId = homestayId;
        this.price = price;
        this.latestDate = latestDate;
    }
}
