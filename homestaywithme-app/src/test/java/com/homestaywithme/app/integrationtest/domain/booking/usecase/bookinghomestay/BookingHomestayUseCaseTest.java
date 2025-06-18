package com.homestaywithme.app.integrationtest.domain.booking.usecase.bookinghomestay;

import com.homestaywithme.app.domain.booking.constant.HomestayAvailabilityStatus;
import com.homestaywithme.app.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.app.domain.booking.repository.BookingRepository;
import com.homestaywithme.app.domain.booking.usecase.BookingHomestayUseCase;
import com.homestaywithme.app.domain.booking.usecase.bookinghomestay.request.dto.request.BookingRequest;
import com.homestaywithme.app.domain.booking.usecase.bookinghomestay.request.dto.response.BookingResponse;
import com.homestaywithme.app.domain.homestay.entity.Homestay;
import com.homestaywithme.app.domain.homestay.repository.HomestayRepository;
import com.homestaywithme.app.domain.payment.vnpay.dto.PaymentResponse;
import com.homestaywithme.app.domain.payment.vnpay.service.VnPayPaymentService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class BookingHomestayUseCaseTest {
    @MockitoBean
    private VnPayPaymentService vnPayPaymentService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BookingHomestayUseCase bookingHomestayUseCase;

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @Transactional
    void bookingHomestay_ShouldWork() {
        // Arrange
        Mockito.when(vnPayPaymentService.createPayment(any()))
                .thenReturn(PaymentResponse
                        .builder()
                        .url("")
                        .build());
        var currentDate = LocalDate.now();
        var homestay = createHomestayAndAvailability(currentDate.plusDays(1), currentDate.plusDays(2));

        var request = BookingRequest
                .builder()
                .homestayId(homestay.getId())
                .from(currentDate.plusDays(1))
                .to(currentDate.plusDays(2))
                .note("note")
                .guests(2)
                .ipAddress("")
                .userId(1L)
                .build();
        entityManager.clear();

        // Act
        var result = (BookingResponse) bookingHomestayUseCase.bookingHomestay(request).getResult();
        var bookingId = result.getBookingId();
        entityManager.clear();

        // Assert
        assertThat(bookingId).isNotNull();
        assertThat(bookingRepository.findById(bookingId)).isPresent();
        var homestayAvailability = getHomestayAvailability(homestay.getId(), currentDate.plusDays(1), currentDate.plusDays(2));
        assertThat(homestayAvailability).isNotEmpty();
        for(var ha: homestayAvailability) {
            assertThat(ha.getStatus()).isEqualTo(HomestayAvailabilityStatus.HELD.getValue());
        }
    }

    private Homestay createHomestayAndAvailability(LocalDate from, LocalDate to) {
        var homestay = new Homestay();
        homestay.setAddress("address");
        homestay.setGuests(2);
        homestay.setName("name");
        homestay.setBathrooms(2);
        homestay.setBedrooms(2);
        homestay.setDescription("description");
        homestay.setLatitude(101D);
        homestay.setLongitude(101D);
        homestay.setPhoneNumber("0969696969");
        homestay.setType(0);
        homestay.setStatus(0);
        homestayRepository.save(homestay);

        for(var date = LocalDate.from(from); !date.isAfter(to); date = date.plusDays(1)) {
            var homestayAvailability = new HomestayAvailability();
            homestayAvailability.setStatus(0);
            homestayAvailability.setDate(date);
            homestayAvailability.setPrice(new BigDecimal("500000"));

            homestayAvailability.setHomestayId(homestay.getId());
            homestay.getHomestayAvailabilities().add(homestayAvailability);
        }

        homestayRepository.flush();

        return homestay;
    }

    private List<HomestayAvailability> getHomestayAvailability(Long homestayId, LocalDate from, LocalDate to) {
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(HomestayAvailability.class);
        var root = query.from(HomestayAvailability.class);
        query
                .select(root)
                .where(cb.equal(root.get("homestayId"), homestayId),
                    cb.greaterThanOrEqualTo(root.get("date"), from),
                    cb.lessThanOrEqualTo(root.get("date"), to));

        return entityManager.createQuery(query).getResultList();
    }
}
