package com.homestaywithme.api;

import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.booking.usecase.BookingHomestayUseCase;
import com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.request.BookingRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingHomestayUseCase bookingHomestayUseCase;

    @Autowired
    public BookingController(BookingHomestayUseCase bookingHomestayUseCase) {
        this.bookingHomestayUseCase = bookingHomestayUseCase;
    }

    @PostMapping
    public Response bookingHomestay(@Valid @RequestBody BookingRequest request) {
        return bookingHomestayUseCase.bookingHomestay(request);
    }
}
