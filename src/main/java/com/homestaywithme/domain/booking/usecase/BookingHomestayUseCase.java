package com.homestaywithme.domain.booking.usecase;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.booking.entity.Booking;
import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.booking.repository.BookingRepository;
import com.homestaywithme.domain.booking.repository.HomestayAvailabilityRepository;
import com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.request.BookingRequest;
import com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.response.BookingResponse;
import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.service.HomestayService;
import com.homestaywithme.domain.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingHomestayUseCase {
    private final ResponseService responseService;
    private final HomestayService homestayService;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingHomestayUseCase(ResponseService responseService,
                                  HomestayService homestayService,
                                  HomestayAvailabilityRepository homestayAvailabilityRepository,
                                  BookingRepository bookingRepository) {
        this.responseService = responseService;
        this.homestayService = homestayService;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Response bookingHomestay(BookingRequest request) {
        validateRequest(request);
        validateHomestay(request);

        var homestayAvailabilities = validateHomestayAvailability(request);
        homestayAvailabilities.forEach(x ->
                homestayAvailabilityRepository.updateStatus(x.getHomestayId(), x.getDate(), 1));

        var homestay = new Homestay();
        homestay.setId(request.getHomestayId());
        var booking = Booking.builder()
                .requestId("")
                .userId(request.getUserId())
                .homestay(homestay)
                .checkinDate(request.getFrom())
                .checkoutDate(request.getTo())
                .note(request.getNote())
                .guests(request.getGuests())
                .currency("USD")
                .totalAmount(calculatePrice(homestayAvailabilities))
                .status(0)
                .build();

        bookingRepository.save(booking);

        return responseService.responseSuccessWithPayload(BookingResponse
                .builder()
                .bookingId(booking.getId())
                .price(booking.getTotalAmount())
                .build());
    }

    private void validateRequest(BookingRequest request) {
        var from = request.getFrom();
        var to = request.getTo();
        var currentDate = LocalDate.now();

        if(from.isAfter(to) || from.isBefore(currentDate)) {
            throw new BusinessException("Check in date is invalid");
        }

        if(request.getGuests() <= 0) {
            throw new BusinessException("Guests must be greater than 0");
        }
    }

    private void validateHomestay(BookingRequest request) {
        var homestay = homestayService.findHomestayById(request.getHomestayId());

        if(homestay.getGuests() < request.getGuests()) {
            throw new BusinessException("Homestay capacity is not enough");
        }
    }

    private List<HomestayAvailability> validateHomestayAvailability(BookingRequest request) {
        var homestayAvailabilities = homestayAvailabilityRepository
                .findAvailableByHomestayIdAndDateWithLock(request.getHomestayId(), request.getFrom(), request.getTo());

        var fromDate = request.getFrom();
        var toDate = request.getTo();
        if(homestayAvailabilities.size() < ChronoUnit.DAYS.between(fromDate, toDate) + 1) {
            throw new BusinessException(MessageFormat.format("Homestay is not available from {0} to {1}", fromDate, toDate));
        }

        return homestayAvailabilities;
    }

    private BigDecimal calculatePrice(List<HomestayAvailability> homestayAvailabilities) {
        return homestayAvailabilities.stream()
                .map(HomestayAvailability::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
