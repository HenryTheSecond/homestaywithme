package com.homestaywithme.application.aop;

import com.homestaywithme.application.dto.response.ApiError;
import com.homestaywithme.application.dto.response.Response;
import com.homestaywithme.application.service.ResponseService;
import com.homestaywithme.domain.shared.constant.ResponseCode;
import com.homestaywithme.domain.shared.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    private final ResponseService responseService;

    @Autowired
    public ApiExceptionHandler(ResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response> handleBusinessException(BusinessException e) {
        log.error("Business exception", e);
        if(e.getResponseCode() != null) {
            return ResponseEntity
                    .status(e.getResponseCode().getCode())
                    .body(responseService.response(e.getResponseCode(), e.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseService.responseWithResponseCode(ResponseCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Argument exception", e);
        List<ApiError> errors = e.getFieldErrors().stream()
                .map(x -> new ApiError.ApiErrorField(x.getField(), x.getDefaultMessage(), x.getRejectedValue()))
                .collect(Collectors.toList());

        return responseService.responseWithInvalidParams(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleUnauthorized(AccessDeniedException e) {
        return responseService.responseWithResponseCode(ResponseCode.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleThrowable(Throwable e) {
        log.error("Unexpected exception", e);
        return responseService.responseWithResponseCode(ResponseCode.INTERNAL_SERVER_ERROR);
    }
}
