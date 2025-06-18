package com.homestaywithme.app.domain.homestay.usecase.updatehomestayprice;

import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.application.service.ResponseService;
import com.homestaywithme.app.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.app.domain.booking.repository.homestayavailability.HomestayAvailabilityRepository;
import com.homestaywithme.app.domain.homestay.service.HomestayService;
import com.homestaywithme.app.domain.homestay.usecase.updatehomestayprice.dto.request.UpdateHomestayPriceRequest;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
public class UpdateHomestayPriceUseCase {
    private final ResponseService responseService;
    private final HomestayService homestayService;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;
    private final EntityManager entityManager;

    @Autowired
    public UpdateHomestayPriceUseCase(ResponseService responseService, HomestayService homestayService, HomestayAvailabilityRepository homestayAvailabilityRepository, EntityManager entityManager) {
        this.responseService = responseService;
        this.homestayService = homestayService;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Response updateHomestayPrice(Long id, UpdateHomestayPriceRequest request) {
        homestayService.checkHomestayExistOrThrow(id);
        var homestayAvailabilityByDate = homestayAvailabilityRepository
                .findByHomestayIdAndDateBetween(id, request.getFrom(), request.getTo())
                .stream().collect(Collectors.toMap(HomestayAvailability::getDate, x -> x));

        for(var date = request.getFrom(); !date.isAfter(request.getTo()); date = date.plusDays(1)) {
            if(homestayAvailabilityByDate.containsKey(date)) {
                homestayAvailabilityByDate.get(date).setPrice(request.getPrice());
                homestayAvailabilityRepository.save(homestayAvailabilityByDate.get(date));
            }
            else {
                var homestayAvailability = new HomestayAvailability();
                homestayAvailability.setHomestayId(id);
                homestayAvailability.setDate(date);
                homestayAvailability.setPrice(request.getPrice());
                homestayAvailability.setStatus(0);
                homestayAvailabilityByDate.put(date, homestayAvailability);
                entityManager.persist(homestayAvailability);
            }
        }

        return responseService.responseSuccessWithPayload(null);
    }
}
