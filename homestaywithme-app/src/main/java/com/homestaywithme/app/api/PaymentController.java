package com.homestaywithme.app.api;

import com.homestaywithme.app.domain.payment.vnpay.usecase.handlevnpayresponse.HandleVnPayResponseUseCase;
import com.homestaywithme.app.domain.payment.vnpay.usecase.handlevnpayresponse.dto.VnPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {
    private final HandleVnPayResponseUseCase handleVnPayResponseUseCase;

    @Autowired
    public PaymentController(HandleVnPayResponseUseCase handleVnPayResponseUseCase) {
        this.handleVnPayResponseUseCase = handleVnPayResponseUseCase;
    }

    @GetMapping("vnpay")
    public VnPayResponse processVnPay(@RequestParam Map<String, String> params) {
        var result = handleVnPayResponseUseCase.handleVnPayResponse(params);
        log.info("VnPay result {} {}", result.getRspCode(), result.getMessage());
        return result;
    }
}
