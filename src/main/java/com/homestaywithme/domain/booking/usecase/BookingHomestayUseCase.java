package com.homestaywithme.domain.booking.usecase;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.booking.constant.BookingStatus;
import com.homestaywithme.domain.booking.constant.Currency;
import com.homestaywithme.domain.booking.constant.BookingExceptionMessage;
import com.homestaywithme.domain.booking.constant.HomestayAvailabilityStatus;
import com.homestaywithme.domain.booking.entity.Booking;
import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.booking.repository.BookingRepository;
import com.homestaywithme.domain.booking.repository.HomestayAvailabilityRepository;
import com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.request.BookingRequest;
import com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.response.BookingResponse;
import com.homestaywithme.domain.homestay.entity.Homestay;
import com.homestaywithme.domain.homestay.service.HomestayService;
import com.homestaywithme.domain.payment.vnpay.dto.PaymentRequest;
import com.homestaywithme.domain.payment.vnpay.service.VnPayPaymentService;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class BookingHomestayUseCase {
    private final ResponseService responseService;
    private final HomestayService homestayService;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;
    private final BookingRepository bookingRepository;
    private final VnPayPaymentService vnPayPaymentService;

    @Autowired
    public BookingHomestayUseCase(ResponseService responseService,
                                  HomestayService homestayService,
                                  HomestayAvailabilityRepository homestayAvailabilityRepository,
                                  BookingRepository bookingRepository,
                                  VnPayPaymentService vnPayPaymentService) {
        this.responseService = responseService;
        this.homestayService = homestayService;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
        this.bookingRepository = bookingRepository;
        this.vnPayPaymentService = vnPayPaymentService;
    }

    @Transactional
    public Response bookingHomestay(BookingRequest request) {
        validateRequest(request);
        validateHomestay(request);

        var homestayAvailabilities = validateHomestayAvailability(request);
        homestayAvailabilities.forEach(x ->
                homestayAvailabilityRepository.updateStatus(x.getHomestayId(),
                        x.getDate(),
                        HomestayAvailabilityStatus.AVAILABLE.getValue()));

        var homestay = new Homestay();
        homestay.setId(request.getHomestayId());
        var booking = Booking.builder()
                .requestId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .homestay(homestay)
                .checkinDate(request.getFrom())
                .checkoutDate(request.getTo())
                .note(request.getNote())
                .guests(request.getGuests())
                .currency(Currency.USD.getValue())
                .totalAmount(calculatePrice(homestayAvailabilities))
                .status(BookingStatus.DRAFT.getValue())
                .build();

        bookingRepository.save(booking);

        var paymentRequest = PaymentRequest
                .builder()
                .userId(booking.getUserId())
                .requestId(booking.getRequestId())
                .amount(booking.getTotalAmount())
                .ipAddress(request.getIpAddress())
                .txnRef(String.valueOf(booking.getId()))
                .build();

        return responseService.responseSuccessWithPayload(BookingResponse
                .builder()
                .bookingId(booking.getId())
                .price(booking.getTotalAmount())
                .payment(vnPayPaymentService.createPayment(paymentRequest))
                .build());
    }

    private void validateRequest(BookingRequest request) {
        var from = request.getFrom();
        var to = request.getTo();
        var currentDate = LocalDate.now();

        if(from.isAfter(to) || from.isBefore(currentDate)) {
            throw new BusinessException(BookingExceptionMessage.CHECK_IN_DATE_INVALID, ResponseCode.BAD_REQUEST);
        }

        if(request.getGuests() <= 0) {
            throw new BusinessException(BookingExceptionMessage.GUESTS_INVALID, ResponseCode.BAD_REQUEST);
        }
    }

    private void validateHomestay(BookingRequest request) {
        var homestay = homestayService.findHomestayById(request.getHomestayId());

        if(homestay.getGuests() < request.getGuests()) {
            throw new BusinessException(BookingExceptionMessage.HOMESTAY_CAPACITY_NOT_ENOUGH, ResponseCode.BAD_REQUEST);
        }
    }

    private List<HomestayAvailability> validateHomestayAvailability(BookingRequest request) {
        var homestayAvailabilities = homestayAvailabilityRepository
                .findAvailableByHomestayIdAndDateWithLock(request.getHomestayId(), request.getFrom(), request.getTo());

        var fromDate = request.getFrom();
        var toDate = request.getTo();
        if(homestayAvailabilities.size() < ChronoUnit.DAYS.between(fromDate, toDate) + 1) {
            throw new BusinessException(MessageFormat.format(BookingExceptionMessage.HOMESTAY_UNAVAILABLE_PATTERN, fromDate, toDate),
                    ResponseCode.BAD_REQUEST);
        }

        return homestayAvailabilities;
    }

    private BigDecimal calculatePrice(List<HomestayAvailability> homestayAvailabilities) {
        return homestayAvailabilities.stream()
                .map(HomestayAvailability::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
