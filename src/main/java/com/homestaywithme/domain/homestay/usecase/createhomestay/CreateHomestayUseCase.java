package com.homestaywithme.domain.homestay.usecase.createhomestay;

import com.homestaywithme.application.dto.response.Meta;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.homestay.service.AmenityService;
import com.homestaywithme.domain.homestay.usecase.createhomestay.dto.request.CreateHomestayRequest;
import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.entity.HomestayAmenity;
import com.homestaywithme.domain.homestay.repository.HomestayAmenityRepository;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CreateHomestayUseCase {
    private final HomestayRepository homestayRepository;
    private final AmenityService amenityService;
    private final GeometryFactory geometryFactory;

    @Autowired
    public CreateHomestayUseCase(HomestayRepository homestayRepository, AmenityService amenityService, GeometryFactory geometryFactory) {
        this.homestayRepository = homestayRepository;
        this.amenityService = amenityService;
        this.geometryFactory = geometryFactory;
    }

    @Transactional
    public Response createHomestay(CreateHomestayRequest request) {
        amenityService.checkAmenityExist(request.getAmenityIds());

        var homestay = new Homestay();
        homestay.setName(request.getName());
        homestay.setDescription(request.getDescription());
        homestay.setType(request.getType());
        homestay.setStatus(request.getStatus());
        homestay.setPhoneNumber(request.getPhoneNumber());
        homestay.setAddress(request.getAddress());
        homestay.setLongitude(request.getLongitude());
        homestay.setLatitude(request.getLatitude());
        homestay.setGeom(geometryFactory.createPoint(new Coordinate(request.getLongitude(),request.getLatitude())));
        homestay.setGuests(request.getGuests());
        homestay.setBedrooms(request.getBedrooms());
        homestay.setBathrooms(request.getBathrooms());
        homestay.setImages(request.getImages());

        homestayRepository.save(homestay);
        createHomestayAmenity(homestay, request.getAmenityIds());
        createHomestayAvailable(homestay, request.getPrice());


        return new Response(new Meta("", "", null), homestay.getId());
    }

    private void createHomestayAmenity(Homestay homestay, List<Integer> amenityIds) {
        amenityIds.forEach(amenityId -> {
            var homestayAmenity = new HomestayAmenity();
            homestayAmenity.setAmenityId(amenityId);
            homestayAmenity.setHomestay(homestay);
            homestayAmenity.setHomestayId(homestay.getId());
            homestay.getHomestayAmenities().add(homestayAmenity);
        });
    }

    private void createHomestayAvailable(Homestay homestay, BigDecimal price) {
        var now = LocalDate.now();
        for(int i = 0; i <= 365; i++) {
            var homestayAvailability = new HomestayAvailability();
            homestayAvailability.setHomestayId(homestay.getId());
            homestayAvailability.setDate(now.plusDays(i));
            homestayAvailability.setPrice(price);
            homestayAvailability.setStatus(0);

            homestay.getHomestayAvailabilities().add(homestayAvailability);
            homestayAvailability.setHomestay(homestay);
        }
    }
}
