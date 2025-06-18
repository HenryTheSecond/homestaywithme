package com.homestaywithme.app.domain.shared.exception;

import com.homestaywithme.app.domain.shared.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private ResponseCode responseCode;

    public BusinessException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }
}
