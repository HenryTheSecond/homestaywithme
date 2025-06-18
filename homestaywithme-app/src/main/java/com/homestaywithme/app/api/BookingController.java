package com.homestaywithme.app.api;

import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.application.util.RequestUtil;
import com.homestaywithme.app.domain.booking.usecase.BookingHomestayUseCase;
import com.homestaywithme.app.domain.booking.usecase.bookinghomestay.request.dto.request.BookingRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingHomestayUseCase bookingHomestayUseCase;

    @Autowired
    public BookingController(BookingHomestayUseCase bookingHomestayUseCase) {
        this.bookingHomestayUseCase = bookingHomestayUseCase;
    }

    @PostMapping
    public Response bookingHomestay(@Valid @RequestBody BookingRequest request,
                                    HttpServletRequest httpServletRequest) {
        var ipAddress = RequestUtil.getIpAddress(httpServletRequest);
        request.setIpAddress(ipAddress);
        return bookingHomestayUseCase.bookingHomestay(request);
    }
}
