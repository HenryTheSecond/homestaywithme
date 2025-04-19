package com.homestaywithme.domain.booking.entity;

import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;*/
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    private LocalDate checkinDate;

    private LocalDate checkoutDate;

    private Integer guests;

    private Integer status;

    private String currency;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal totalAmount;

    private String note;

    private String requestId;
}
