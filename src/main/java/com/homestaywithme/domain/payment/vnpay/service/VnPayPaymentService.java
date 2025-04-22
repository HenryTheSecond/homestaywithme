package com.homestaywithme.domain.payment.vnpay.service;

import com.homestaywithme.domain.booking.constant.Currency;
import com.homestaywithme.domain.payment.vnpay.configuration.VnPayConfiguration;
import com.homestaywithme.domain.payment.vnpay.constant.VnPayParamConstant;
import com.homestaywithme.domain.payment.vnpay.dto.PaymentRequest;
import com.homestaywithme.domain.payment.vnpay.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnPayPaymentService {


    private final VnPayConfiguration configuration;
    private final CryptoService cryptoService;

    @Autowired
    public VnPayPaymentService(VnPayConfiguration configuration, CryptoService cryptoService) {
        this.configuration = configuration;
        this.cryptoService = cryptoService;
    }

    public PaymentResponse createPayment(PaymentRequest request) {
        var amount = request.getAmount().multiply(VnPayParamConstant.MULTIPLIER).longValue();

        Map<String, String> params = new HashMap<>();

        params.put(VnPayParamConstant.VERSION, configuration.getVersion());
        params.put(VnPayParamConstant.COMMAND, configuration.getCommand());
        params.put(VnPayParamConstant.TMN_CODE, configuration.getTmnCode());
        params.put(VnPayParamConstant.AMOUNT, String.valueOf(amount));
        params.put(VnPayParamConstant.CURRENCY, Currency.VND.getValue());
        params.put(VnPayParamConstant.TXN_REF, request.getTxnRef());
        params.put(VnPayParamConstant.RETURN_URL, configuration.getReturnUrl());

        var createdDate = getCreatedDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put(VnPayParamConstant.CREATE_DATE, formatter.format(createdDate.getTime()));
        createdDate.add(Calendar.MINUTE, configuration.getTimeout());
        params.put(VnPayParamConstant.EXPIRE_DATE, formatter.format(createdDate.getTime()));

        params.put(VnPayParamConstant.IP_ADDRESS, request.getIpAddress());
        params.put(VnPayParamConstant.LOCALE, "vn");
        params.put(VnPayParamConstant.ORDER_INFO, "Thanh toan dat homestay");
        params.put(VnPayParamConstant.ORDER_TYPE, "other");

        var url = buildPaymentQueryUrl(params);
        return PaymentResponse
                .builder()
                .url(url)
                .build();
    }

    private Calendar getCreatedDate() {
        return Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    }

    private String buildPaymentQueryUrl(Map<String, String> params) {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String secureHash = cryptoService.sign(hashData.toString());
        query.append("&vnp_SecureHash=").append(secureHash);
        return configuration.getInitPaymentUrl() + "?" + query;
    }
}
