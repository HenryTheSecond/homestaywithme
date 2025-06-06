package com.homestaywithme.domain.homestay.usecase.gethomestaybyid;

import com.homestaywithme.application.redis.Constant;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.homestay.usecase.gethomestaybyid.dto.HomestayDto;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomestayByIdUseCase {
    private final HomestayRepository homestayRepository;
    private final HomestayMapper homestayMapper;
    private final ResponseService responseService;

    @Cacheable(value = Constant.HOMESTAY_CACHE, key = "'homestay_' + #id")
    public HomestayDto getHomestayDetailById(Long id) {
        var homestay = homestayRepository.getHomestayById(id).orElseThrow(() -> new BusinessException(String.format(HomestayExceptionMessage.HOMESTAY_NOT_FOUND_FORMAT, id),
                ResponseCode.BAD_REQUEST));
        return homestayMapper.homestayToHomestayDto(homestay);
    }
}
