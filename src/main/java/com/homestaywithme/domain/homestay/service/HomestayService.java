package com.homestaywithme.domain.homestay.service;

import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HomestayService {
    @Autowired
    private HomestayRepository homestayRepository;

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
        return new BusinessException("Not found homestay with Id: " + id);
    }
}
