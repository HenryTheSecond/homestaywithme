package com.homestaywithme.integrationtest.domain.homestay.usecase.createhomestay;

import com.homestaywithme.domain.homestay.entity.Amenity;
import com.homestaywithme.domain.homestay.repository.AmenityRepository;
import com.homestaywithme.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.domain.homestay.usecase.createhomestay.CreateHomestayUseCase;
import com.homestaywithme.domain.homestay.usecase.createhomestay.dto.request.CreateHomestayRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CreateHomestayUseCaseTest {
    private final CreateHomestayUseCase createHomestayUseCase;
    private final HomestayRepository homestayRepository;
    private final AmenityRepository amenityRepository;

    @Autowired
    public CreateHomestayUseCaseTest(CreateHomestayUseCase createHomestayUseCase,
                                     HomestayRepository homestayRepository,
                                     AmenityRepository amenityRepository) {
        this.createHomestayUseCase = createHomestayUseCase;
        this.homestayRepository = homestayRepository;
        this.amenityRepository = amenityRepository;
    }

    @Test
    @Transactional
    void createHomestay_ShouldWork() {
        // Arrange
        List<Amenity> amenities = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            var amenity = new Amenity();
            amenity.setName("Amenity " + i);
            amenity.setIcon("");
            amenities.add(amenity);
        }
        amenityRepository.saveAllAndFlush(amenities);

        var request = CreateHomestayRequest
                .builder()
                .address("address")
                .guests(1)
                .images(new ArrayList<>())
                .name("name")
                .bathrooms(2)
                .bedrooms(2)
                .price(new BigDecimal("8000000"))
                .amenityIds(amenities.stream().map(Amenity::getId).toList())
                .description("description")
                .latitude(101D)
                .longitude(101D)
                .phoneNumber("0969696969")
                .type(0)
                .status(0)
                .build();

        // Act
        var response = createHomestayUseCase.createHomestay(request);

        // Assert
        Long id = (Long) response.getResult();
        assertThat(id).isNotNull();
        var homestay = homestayRepository.findById(id).orElse(null);
        assertThat(homestay).isNotNull();
        var homestayAmenities = homestay.getHomestayAmenities();
        assertThat(homestayAmenities).hasSize(4);
    }
}
