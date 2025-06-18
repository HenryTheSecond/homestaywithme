package com.homestaywithme.app.domain.booking.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HomestayAvailabilityStatus {
    AVAILABLE(0),
    HELD(1),
    BOOKED(2),
    SERVED(3)
    ;

    private final int value;
}
