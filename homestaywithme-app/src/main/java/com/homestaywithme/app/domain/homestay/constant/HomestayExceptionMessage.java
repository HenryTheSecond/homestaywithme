package com.homestaywithme.app.domain.homestay.constant;

public class HomestayExceptionMessage {
    private HomestayExceptionMessage() {
        throw new IllegalStateException();
    }

    public static final String HOMESTAY_NOT_FOUND_FORMAT = "Not found homestay with Id: %d";
    public static final String AMENITY_INVALID_FORMAT = "Invalid amenity: %s";
    public static final String SORT_NOT_SUPPORTED_FORMAT = "Not supported sort by %s";
    public static final String ORDER_NOT_SUPPORTED_FORMAT = "Not supported order %s";
}
