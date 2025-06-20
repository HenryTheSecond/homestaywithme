package com.homestaywithme.consumer.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomestayAvailabilityId implements Serializable {
    private Long homestayId;
    private LocalDate date;
}
