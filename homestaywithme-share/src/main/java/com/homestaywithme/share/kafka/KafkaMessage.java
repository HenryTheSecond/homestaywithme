package com.homestaywithme.share.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage<T> {
    private Meta meta;
    private T payload;
}