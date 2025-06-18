package com.homestaywithme.app.domain.booking.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    USD("USD"),
    VND("VND"),
    ;

    private final String value;
}
