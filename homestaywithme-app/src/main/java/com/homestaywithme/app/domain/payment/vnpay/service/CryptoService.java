package com.homestaywithme.app.domain.payment.vnpay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class CryptoService {
    private static final String HMAC_SHA512 = "HmacSHA512";

    private final Mac mac;

    @Autowired
    public CryptoService(@Value("${payment.vnpay.secret-key}") String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA512);
        mac = Mac.getInstance(HMAC_SHA512);
        mac.init(secretKeySpec);
    }

    public String sign(String data) {
        var hmacBytes = mac.doFinal(data.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hmacBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
