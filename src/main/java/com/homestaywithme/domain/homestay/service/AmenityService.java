package com.homestaywithme.domain.homestay.service;

import com.homestaywithme.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.domain.homestay.entity.Amenity;
import com.homestaywithme.domain.homestay.repository.AmenityRepository;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.constant.StringConstant;
import com.homestaywithme.domain.shared.exception.BusinessException;
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
