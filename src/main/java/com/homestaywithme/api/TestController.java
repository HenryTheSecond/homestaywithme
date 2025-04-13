package com.homestaywithme.api;

import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.booking.entity.HomestayAvailabilityId;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private HomestayRepository homestayRepository;

    @PostMapping("/homestays")
    public void AddHomeStay() {
        GeometryFactory geometryFactory = new GeometryFactory();

        Homestay homestay = new Homestay();
        homestay.setName("name");
        homestay.setDescription("description");
        homestay.setType(1);
        homestay.setHost(null);
        homestay.setStatus(0);
        homestay.setPhoneNumber("09797979");
        homestay.setAddress("address");
        homestay.setLongitude(105.85054073130831);
        homestay.setLatitude(21.010219168557075);
        homestay.setImages(Arrays.asList("abc", "def"));
        homestay.setGuests(1);
        homestay.setBedrooms(2);
        homestay.setBathrooms(3);
        homestay.setVersion(1L);
        homestay.setGeom(geometryFactory.createPoint(new Coordinate(105.85054073130831, 21.010219168557075)));
        homestayRepository.save(homestay);
    }

    @GetMapping("test-equal-and-hash-code-composite-id")
    public boolean testEqualAndHashCode() {
        HomestayAvailabilityId id1 = new HomestayAvailabilityId();
        id1.setHomestayId(1L);
        id1.setDate(LocalDate.of(2025, 1, 1));

        HomestayAvailabilityId id2 = new HomestayAvailabilityId();
        id2.setHomestayId(1L);
        id2.setDate(LocalDate.of(2025, 1, 1));

        return id1.hashCode() == id2.hashCode();
    }
}
