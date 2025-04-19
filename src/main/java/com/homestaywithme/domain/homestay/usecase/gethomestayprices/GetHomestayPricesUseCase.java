package com.homestaywithme.domain.homestay.usecase.gethomestayprices;

import com.homestaywithme.application.dto.response.Meta;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.booking.repository.HomestayAvailabilityRepository;
import com.homestaywithme.domain.homestay.service.HomestayService;
import com.homestaywithme.domain.homestay.usecase.createhomestay.dto.HomestayPriceDto;
import com.homestaywithme.domain.homestay.usecase.gethomestayprices.dto.request.GetHomestayPricesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetHomestayPricesUseCase {
    private final HomestayService homestayService;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;

    @Autowired
    public GetHomestayPricesUseCase(HomestayService homestayService, HomestayAvailabilityRepository homestayAvailabilityRepository) {
        this.homestayService = homestayService;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
    }

    public Response getHomestayPrices(Long id, GetHomestayPricesRequest request) {
        homestayService.checkHomestayExistOrThrow(id);
        var homestayPrices = homestayAvailabilityRepository
                .findByHomestayIdAndDateBetween(id, request.getFrom(), request.getTo())
                .stream()
                .map(x -> new HomestayPriceDto(x.getHomestayId(), x.getDate(), x.getPrice()))
                .toList();
        return new Response(new Meta("", "", null), homestayPrices);
    }
}
