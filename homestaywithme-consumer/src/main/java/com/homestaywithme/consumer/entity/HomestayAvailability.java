package com.homestaywithme.consumer.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@IdClass(HomestayAvailabilityId.class)
@Data
@NoArgsConstructor
@Builder
public class HomestayAvailability {
    @Id
    @Column(name = "homestay_id")
    private Long homestayId;
    @Id
    private LocalDate date;

    private BigDecimal price;
    private Integer status;

    public HomestayAvailability(Long homestayId, LocalDate date, BigDecimal price, Integer status) {
        this.homestayId = homestayId;
        this.date = date;
        this.price = price;
        this.status = status;
    }
}
