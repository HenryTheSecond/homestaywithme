package com.homestaywithme.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meta {
    private Integer code;
    private String message;
    private List<ApiError> errors;
    private Map<String, Object> extraData;
}
