package com.homestaywithme.domain.homestay.usecase.createhomestay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHomestayRequest {
    private String name;
    private String description;
    private Integer type;
    private Integer status;
    private String phoneNumber;
    private String address;
    private Double longitude;
    private Double latitude;
    private Integer guests;
    private Integer bedrooms;
    private Integer bathrooms;
    private List<Integer> amenityIds;
    private List<String> images;
    private BigDecimal price;
}
