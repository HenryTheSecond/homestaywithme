package com.homestaywithme.domain.homestay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class HomestayAmenityId implements Serializable {
    private Long homestayId;
    private Integer amenityId;
}
