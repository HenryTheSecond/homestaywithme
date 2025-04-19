package com.homestaywithme.domain.homestay.usecase.updatehomestay;

import com.homestaywithme.application.dto.response.Meta;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.entity.HomestayAmenity;
import com.homestaywithme.domain.homestay.service.AmenityService;
import com.homestaywithme.domain.homestay.service.HomestayService;
import com.homestaywithme.domain.homestay.usecase.updatehomestay.dto.request.UpdateHomestayRequest;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import jakarta.persistence.EntityManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UpdateHomestayUseCase {
    private final HomestayService homestayService;
    private final HomestayRepository homestayRepository;
    private final AmenityService amenityService;
    private final GeometryFactory geometryFactory;
    private final EntityManager entityManager;

    @Autowired
    public UpdateHomestayUseCase(HomestayService homestayService, HomestayRepository homestayRepository, AmenityService amenityService, GeometryFactory geometryFactory, EntityManager entityManager) {
        this.homestayService = homestayService;
        this.homestayRepository = homestayRepository;
        this.amenityService = amenityService;
        this.geometryFactory = geometryFactory;
        this.entityManager = entityManager;
    }

    @Transactional
    public Response updateHomestay(Long id, UpdateHomestayRequest request) {
        amenityService.checkAmenityExist(request.getAmenityIds());
        var homestay = homestayService.findHomestayById(id);

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

        updateAmenity(homestay, request.getAmenityIds());
        homestayRepository.save(homestay);
        return new Response(new Meta("", "", null), homestay.getId());
    }

    @Transactional
    private void updateAmenity(Homestay homestay, List<Integer> amenityIds) {
        var currentAmenities = homestay.getHomestayAmenities();
        var currentAmenityById = currentAmenities.stream()
                .collect(Collectors.toMap(HomestayAmenity::getAmenityId, x -> x));

        for(var currentAmenityId: currentAmenityById.keySet()) {
            if(!amenityIds.contains(currentAmenityId)) {
                currentAmenities.remove(currentAmenityById.get(currentAmenityId));
                currentAmenityById.get(currentAmenityId).setHomestayId(null);
                currentAmenityById.get(currentAmenityId).setHomestay(null);
            }
        }

        for(var id: amenityIds) {
            if(!currentAmenityById.containsKey(id)) {
                var homestayAmenity = new HomestayAmenity();
                homestayAmenity.setHomestayId(homestay.getId());
                homestayAmenity.setAmenityId(id);
                currentAmenities.add(homestayAmenity);
                entityManager.persist(homestayAmenity);
            }
        }
    }
}
