package com.homestaywithme.share.kafka;

public record Meta(String messageId, String originalMessageId, String serviceId, long timestamp) {
    public Meta(String messageId, String serviceId, long timestamp) {
        this(messageId, null, serviceId, timestamp);
    }
}
