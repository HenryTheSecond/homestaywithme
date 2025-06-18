package com.homestaywithme.app.domain.homestay.usecase.searchhomestay.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchHomestaySort {
    DISTANCE("h.geom <-> d.geom"),
    PRICE("totalPrice")
    ;
    private final String value;
}
