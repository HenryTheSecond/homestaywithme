package com.homestaywithme.application.dto.response;

public interface ApiError {
    String message();

    record ApiErrorField(String field, String message, Object rejectedValue) implements ApiError {}
}
