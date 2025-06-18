package com.homestaywithme.app.domain.booking.entity;

import com.homestaywithme.app.domain.homestay.entity.Homestay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Builder
public class HomestayAvailability {
    @Id
    @Column(name = "homestay_id")
    private Long homestayId;
    @Id
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    private BigDecimal price;
    private Integer status;

    public HomestayAvailability(Long homestayId, LocalDate date, BigDecimal price, Integer status) {
        this.homestayId = homestayId;
        this.date = date;
        this.price = price;
        this.status = status;
    }
}
