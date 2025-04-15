package com.homestaywithme.domain.homestay.usecase.searchhomestay.dto;

import java.math.BigDecimal;
import java.util.List;

public interface SearchHomestayDto {
    /*private Long homestayId;
    private String name;
    private Double longitude;
    private Double latitude;
    private String description;
    private BigDecimal pricePerDay;
    private BigDecimal totalPrice;
    private List<String> images;*/

    Long getHomestayId();
    String getName();
    Double getLongitude();
    Double getLatitude();
    String getDescription();
    BigDecimal getPricePerDay();
    BigDecimal getTotalPrice();
    List<String> getImages();
}
