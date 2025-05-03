package com.homestaywithme.domain.booking.usecase.bookinghomestay.request.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    @NotNull(message = "user is required")
    private Long userId;

    @NotNull(message = "homestay is required")
    private Long homestayId;

    @NotNull(message = "check in date is required")
    private LocalDate from;

    @NotNull(message = "check out date is required")
    private LocalDate to;

    @NotNull(message = "guests is required")
    private Integer guests;

    private String note;

    private String ipAddress;
}
