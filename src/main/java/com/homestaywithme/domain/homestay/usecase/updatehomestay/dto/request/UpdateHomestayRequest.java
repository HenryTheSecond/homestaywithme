package com.homestaywithme.domain.homestay.usecase.updatehomestay.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UpdateHomestayRequest {
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
}
