package com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.response;

import com.homestaywithme.domain.payment.vnpay.dto.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private BigDecimal price;
    private PaymentResponse payment;
}
