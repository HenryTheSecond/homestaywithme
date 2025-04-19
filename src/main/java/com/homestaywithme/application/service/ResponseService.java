package com.homestaywithme.application.service;

import com.homestaywithme.application.dto.response.Meta;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import org.springframework.stereotype.Service;

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

    public Response responseWithResponseConstant(ResponseCode responseCode) {
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
}
