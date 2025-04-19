package com.homestaywithme.application.service;

import com.homestaywithme.application.dto.response.Meta;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.domain.shared.constant.ResponseConstant;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public Response responseSuccessWithPayload(Object payload) {
        var meta = Meta
                .builder()
                .code(ResponseConstant.SUCCESS.getCode())
                .message(ResponseConstant.SUCCESS.getType())
                .build();
        return new Response(meta, payload);
    }

    public Response responseWithResponseConstant(ResponseConstant responseConstant) {
        var meta = Meta
                .builder()
                .code(responseConstant.getCode())
                .message(responseConstant.getType())
                .build();
        return new Response(meta, null);
    }

    public Response response(ResponseConstant responseConstant, Object payload) {
        var meta = Meta
                .builder()
                .code(responseConstant.getCode())
                .message(responseConstant.getType())
                .build();
        return new Response(meta, payload);
    }
}
