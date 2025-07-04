package com.homestaywithme.app.application.service;

import com.homestaywithme.app.application.dto.response.ApiError;
import com.homestaywithme.app.application.dto.response.Meta;
import com.homestaywithme.app.application.dto.response.Response;
import com.homestaywithme.app.domain.shared.constant.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    public Response responseSuccessWithPayload(Object payload) {
        var meta = Meta
                .builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getType())
                .build();
        return new Response(meta, payload);
    }

    public Response responseWithResponseCode(ResponseCode responseCode) {
        var meta = Meta
                .builder()
                .code(responseCode.getCode())
                .message(responseCode.getType())
                .build();
        return new Response(meta, null);
    }

    public Response response(ResponseCode responseCode, Object payload) {
        var meta = Meta
                .builder()
                .code(responseCode.getCode())
                .message(responseCode.getType())
                .build();
        return new Response(meta, payload);
    }

    public Response responseWithInvalidParams(List<ApiError> errors) {
        var meta = Meta
                .builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .message(ResponseCode.BAD_REQUEST.getType())
                .errors(errors)
                .build();
        return new Response(meta, null);
    }
}
