package com.homestaywithme.app.domain.homestay.usecase.gethomestayprices;

import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.application.service.ResponseService;
import com.homestaywithme.app.domain.booking.repository.homestayavailability.HomestayAvailabilityRepository;
import com.homestaywithme.app.domain.homestay.service.HomestayService;
import com.homestaywithme.app.domain.homestay.usecase.createhomestay.dto.HomestayPriceDto;
import com.homestaywithme.app.domain.homestay.usecase.gethomestayprices.dto.request.GetHomestayPricesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetHomestayPricesUseCase {
    private final ResponseService responseService;
    private final HomestayService homestayService;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;

    @Autowired
    public GetHomestayPricesUseCase(ResponseService responseService, HomestayService homestayService, HomestayAvailabilityRepository homestayAvailabilityRepository) {
        this.responseService = responseService;
        this.homestayService = homestayService;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
    }

    @Transactional(readOnly = true)
    public Response getHomestayPrices(Long id, GetHomestayPricesRequest request) {
        homestayService.checkHomestayExistOrThrow(id);
        var homestayPrices = homestayAvailabilityRepository
                .findByHomestayIdAndDateBetween(id, request.getFrom(), request.getTo())
                .stream()
                .map(x -> new HomestayPriceDto(x.getHomestayId(), x.getDate(), x.getPrice()))
                .toList();
        return responseService.responseSuccessWithPayload(homestayPrices);
    }
}
