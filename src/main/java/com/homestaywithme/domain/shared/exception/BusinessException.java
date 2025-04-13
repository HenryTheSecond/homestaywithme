package com.homestaywithme.domain.shared.exception;

import lombok.*;

@NoArgsConstructor
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
