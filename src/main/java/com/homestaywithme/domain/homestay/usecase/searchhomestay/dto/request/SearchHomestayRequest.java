package com.homestaywithme.domain.homestay.usecase.searchhomestay.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchHomestayRequest {
    private Double longitude;
    private Double latitude;
    private Double distance;
    private LocalDate from;
    private LocalDate to;
    private String sort = "";
    private String order = "";
    private Integer page;
    private Integer pageSize;
}
