package com.homestaywithme.domain.homestay.service;

import com.homestaywithme.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HomestayService {
    private final HomestayRepository homestayRepository;

    @Autowired
    public HomestayService(HomestayRepository homestayRepository) {
        this.homestayRepository = homestayRepository;
    }

    @Transactional
    public Homestay findHomestayById(Long id) {
        return homestayRepository
                .findById(id)
                .orElseThrow(() -> getHomestayNotExistException(id));
    }

    @Transactional
    public boolean checkHomestayExist(Long id) {
        return homestayRepository.existsById(id);
    }

    @Transactional
    public void checkHomestayExistOrThrow(Long id) {
        if(!checkHomestayExist(id)) {
            throw getHomestayNotExistException(id);
        }
    }

    private BusinessException getHomestayNotExistException(Long id) {
        return new BusinessException(String.format(HomestayExceptionMessage.HOMESTAY_NOT_FOUND_FORMAT, id),
                ResponseCode.BAD_REQUEST);
    }
}
