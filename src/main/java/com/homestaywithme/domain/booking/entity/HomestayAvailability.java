package com.homestaywithme.domain.booking.entity;

import com.homestaywithme.domain.homestay.entity.Homestay;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@IdClass(HomestayAvailabilityId.class)
@Data
@NoArgsConstructor
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
}
