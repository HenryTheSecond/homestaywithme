package com.homestaywithme.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class Meta {
    private String status;
    private String message;
    private Map<String, Object> extraData;
}
