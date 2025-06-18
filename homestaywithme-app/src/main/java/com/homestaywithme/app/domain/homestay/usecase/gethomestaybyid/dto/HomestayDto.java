package com.homestaywithme.app.domain.homestay.usecase.gethomestaybyid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomestayDto {
    private Long id;
    private String name;
    private String description;
    private int type;
    private Integer status;
    private String phoneNumber;
    private String address;
    private Double longitude;
    private Double latitude;
    private List<String> images;
    private Integer guests;
    private Integer bedrooms;
    private Integer bathrooms;
    List<AmenityDto> homestayAmenities = new ArrayList<>();
}
