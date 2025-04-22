package com.homestaywithme.domain.payment.vnpay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long userId;
    private BigDecimal amount;
    private String txnRef;
    private String requestId;
    private String ipAddress;
}
