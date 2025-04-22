package com.homestaywithme.domain.payment.vnpay.usecase.handlevnpayresponse;

import com.homestaywithme.domain.booking.constant.BookingStatus;
import com.homestaywithme.domain.booking.entity.Booking;
import com.homestaywithme.domain.booking.repository.BookingRepository;
import com.homestaywithme.domain.payment.vnpay.constant.VnPayParamConstant;
import com.homestaywithme.domain.payment.vnpay.service.CryptoService;
import com.homestaywithme.domain.payment.vnpay.usecase.handlevnpayresponse.dto.VnPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class HandleVnPayResponseUseCase {
    private final CryptoService cryptoService;
    private final BookingRepository bookingRepository;

    public HandleVnPayResponseUseCase(CryptoService cryptoService,
                                      BookingRepository bookingRepository) {
        this.cryptoService = cryptoService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public VnPayResponse handleVnPayResponse(Map<String, String> params) {
        try {
            if(!verifySignature(params)) {
                return VnPayResponse.SIGNATURE_FAILED;
            }

            var optionalBooking = getBooking(params.get(VnPayParamConstant.TXN_REF));
            if(optionalBooking.isEmpty()) {
                return VnPayResponse.ORDER_NOT_FOUND;
            }
            var booking = optionalBooking.get();

            var amount = new BigDecimal(params.get(VnPayParamConstant.AMOUNT)).divide(VnPayParamConstant.MULTIPLIER);
            if(!booking.getTotalAmount().equals(amount)) {
                return VnPayResponse.INVALID_AMOUNT;
            }

            if(booking.getStatus() != BookingStatus.DRAFT.getValue()) {
                return VnPayResponse.ORDER_ALREADY_CONFIRMED;
            }

            booking.setStatus(BookingStatus.BOOKED.getValue());
            bookingRepository.save(booking);
            return VnPayResponse.SUCCESS;
        } catch (Exception e) {
            log.error("Unknown exception", e);
            return VnPayResponse.UNKNOWN_ERROR;
        }
    }

    public boolean verifySignature(Map<String, String> params) {
        var requestSecureHash = params.get(VnPayParamConstant.SECURE_HASH);
        params.remove(VnPayParamConstant.SECURE_HASH);
        params.remove(VnPayParamConstant.SECURE_HASH_TYPE);

        var hashPayload = new StringBuilder();
        var fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        var itr = fieldNames.iterator();
        while(itr.hasNext()) {
            var fieldName = itr.next();
            var fieldValue = params.get(fieldName);
            if(fieldValue != null && !fieldValue.isEmpty()) {
                hashPayload.append(fieldName);
                hashPayload.append("=");
                hashPayload.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if(itr.hasNext()) {
                    hashPayload.append("&");
                }
            }
        }

        var secureHash = cryptoService.sign(hashPayload.toString());
        return secureHash.equals(requestSecureHash);
    }

    private Optional<Booking> getBooking(String txnRef) {
        try {
            var bookingId = Long.parseLong(txnRef);
            return bookingRepository.findById(bookingId);
        }
        catch (NumberFormatException e) {
            log.error("txn ref format can not be parsed to booking id");
            return Optional.empty();
        }
    }
}
