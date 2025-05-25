package com.homestaywithme.domain.homestay.usecase.gethomestaybyid;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.redis.key.HomestayKey;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.homestay.constant.HomestayExceptionMessage;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomestayByIdUseCase {
    private final HomestayRepository homestayRepository;
    private final HomestayMapper homestayMapper;
    private final ResponseService responseService;
    private final RedisTemplate<String, Object> redisTemplate;

    public Response getHomestayDetailById(Long id) {
        String key = HomestayKey.getHomestayKey(id);
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            return responseService
                    .responseSuccessWithPayload(redisTemplate.opsForValue().get(key));
        }

        var homestay = homestayRepository.getHomestayById(id).orElseThrow(() -> new BusinessException(String.format(HomestayExceptionMessage.HOMESTAY_NOT_FOUND_FORMAT, id),
                ResponseCode.BAD_REQUEST));
        var homestayDto = homestayMapper.homestayToHomestayDto(homestay);
        redisTemplate.opsForValue().set(key, homestayDto);

        return responseService
                .responseSuccessWithPayload(homestayDto);
    }
}
