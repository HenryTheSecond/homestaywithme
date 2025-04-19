package com.homestaywithme.domain.homestay.usecase.updatehomestayprice;

import com.homestaywithme.application.dto.response.Meta;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.booking.repository.HomestayAvailabilityRepository;
import com.homestaywithme.domain.homestay.service.HomestayService;
import com.homestaywithme.domain.homestay.usecase.updatehomestayprice.dto.request.UpdateHomestayPriceRequest;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
public class UpdateHomestayPriceUseCase {
    @Autowired
    private HomestayService homestayService;
    @Autowired
    private HomestayAvailabilityRepository homestayAvailabilityRepository;
    @Autowired
    private EntityManager entityManager;

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

        return new Response(new Meta("", "", null), null);
    }
}
