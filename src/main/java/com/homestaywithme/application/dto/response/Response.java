package com.homestaywithme.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response {
    private Meta meta;
    private Object result;
}
