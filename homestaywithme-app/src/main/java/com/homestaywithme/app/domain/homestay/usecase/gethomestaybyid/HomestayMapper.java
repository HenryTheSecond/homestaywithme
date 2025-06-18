package com.homestaywithme.app.domain.homestay.usecase.gethomestaybyid;

import com.homestaywithme.app.domain.homestay.entity.Amenity;
import com.homestaywithme.app.domain.homestay.entity.Homestay;
import com.homestaywithme.app.domain.homestay.entity.HomestayAmenity;
import com.homestaywithme.app.domain.homestay.usecase.gethomestaybyid.dto.AmenityDto;
import com.homestaywithme.app.domain.homestay.usecase.gethomestaybyid.dto.HomestayDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HomestayMapper {
    @Mapping(source = "homestayAmenities", target = "homestayAmenities")
    HomestayDto homestayToHomestayDto(Homestay homestay);
    AmenityDto amenityToAmenityDto(Amenity amenity);

    default List<AmenityDto> mapAmenities(List<HomestayAmenity> homestayAmenities) {
        if (homestayAmenities == null) {
            return new ArrayList<>();
        }
        return homestayAmenities.stream()
                .map(h -> amenityToAmenityDto(h.getAmenity()))
                .toList();
    }
}
