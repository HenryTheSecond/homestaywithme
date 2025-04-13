package com.homestaywithme.domain.booking.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class HomestayAvailabilityId implements Serializable {
    private Long homestayId;
    private LocalDate date;
}
