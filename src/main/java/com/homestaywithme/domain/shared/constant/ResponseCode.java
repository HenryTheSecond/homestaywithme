package com.homestaywithme.domain.shared.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("SUCCESS", 200),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 500),
    BAD_REQUEST("BAD_REQUEST", 400),
    INVALID_PARAMS("INVALID_PARAMS", 400)
    ;

    private final String type;
    private final int code;
}
