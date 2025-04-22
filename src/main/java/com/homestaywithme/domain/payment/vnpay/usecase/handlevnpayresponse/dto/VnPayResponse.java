package com.homestaywithme.domain.payment.vnpay.usecase.handlevnpayresponse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VnPayResponse {
    SUCCESS("00", "Successful"),
    SIGNATURE_FAILED("97", "Signature failed"),
    ORDER_NOT_FOUND("01", "Order not found"),
    INVALID_AMOUNT("04", "Invalid Amount"),
    ORDER_ALREADY_CONFIRMED("02", "Order already confirmed"),
    UNKNOWN_ERROR("99", "Unknown error")
    ;

    @JsonProperty("RspCode")
    private final String rspCode;
    @JsonProperty("Message")
    private final String message;
}
