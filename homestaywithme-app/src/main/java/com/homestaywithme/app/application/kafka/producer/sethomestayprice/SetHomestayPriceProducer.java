package com.homestaywithme.app.application.kafka.producer.sethomestayprice;

import com.homestaywithme.share.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SetHomestayPriceProducer {
    private final String setHomestayPriceTopic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SetHomestayPriceProducer(@Value("${spring.kafka.topic.set-homestay-price-topic}") String setHomestayPriceTopic,
                                    KafkaTemplate<String, Object> kafkaTemplate) {
        this.setHomestayPriceTopic = setHomestayPriceTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(KafkaMessage<SetHomestayPriceMessage> message) {
        try {
            kafkaTemplate.send(setHomestayPriceTopic, message);
        } catch (Exception e) {
            log.error("Fail to produce message", e);
        }
    }
}
