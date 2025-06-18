package com.homestaywithme.app.domain.homestay.service;

import com.homestaywithme.app.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.app.domain.homestay.entity.Amenity;
import com.homestaywithme.app.domain.homestay.repository.AmenityRepository;
import com.homestaywithme.app.domain.shared.constant.ResponseCode;
import com.homestaywithme.app.domain.shared.constant.StringConstant;
import com.homestaywithme.app.domain.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;

    @Autowired
    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Transactional
    public void checkAmenityExist(List<Integer> amenityIds) {
        var existAmenity = amenityRepository.findAllById(amenityIds)
                .stream()
                .map(Amenity::getId)
                .toList();
        if(existAmenity.size() != amenityIds.size()) {
            var notExistIds = amenityIds
                    .stream()
                    .filter(x -> !existAmenity.contains(x))
                    .map(Object::toString).toList();
            throw new BusinessException(
                    String.format(HomestayExceptionMessage.AMENITY_INVALID_FORMAT, String.join(StringConstant.COMMA_SEPARATOR, notExistIds)),
                    ResponseCode.BAD_REQUEST);
        }
    }
}
