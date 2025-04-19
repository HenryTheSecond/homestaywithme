package com.homestaywithme.api;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.booking.usecase.BookingHomestayUseCase;
import com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired private BookingHomestayUseCase bookingHomestayUseCase;

    @PostMapping
    public Response bookingHomestay(@RequestBody BookingRequest request) {
        return bookingHomestayUseCase.bookingHomestay(request);
    }
}
