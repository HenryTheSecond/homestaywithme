package com.homestaywithme.application.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {
    private RequestUtil() {
        throw new IllegalStateException();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if(xForwardedForHeader == null) {
            var remoteAddress = request.getRemoteAddr();
            if(remoteAddress == null) {
                remoteAddress = "127.0.0.1";
            }

            return remoteAddress;
        }

        return xForwardedForHeader.split(",")[0].trim();
    }
}
