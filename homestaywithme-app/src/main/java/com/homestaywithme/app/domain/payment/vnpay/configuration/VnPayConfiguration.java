package com.homestaywithme.app.domain.payment.vnpay.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payment.vnpay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VnPayConfiguration {
    private String version;
    private String command;
    private String tmnCode;
    private String secretKey;
    private String initPaymentUrl;
    private String returnUrl;
    private int timeout;
}
