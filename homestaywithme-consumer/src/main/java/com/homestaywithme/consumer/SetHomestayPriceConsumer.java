package com.homestaywithme.consumer;

import com.homestaywithme.consumer.entity.HomestayAvailability;
import com.homestaywithme.consumer.model.SetHomestayPriceMessage;
import com.homestaywithme.consumer.model.SetHomestayPriceMessageWrapper;
import com.homestaywithme.share.kafka.KafkaMessage;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetHomestayPriceConsumer {
    private final EntityManager entityManager;

    @Transactional
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2),
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            autoCreateTopics = "true",
            include = {RetriableException.class}
    )
    @KafkaListener(topics = "${spring.kafka.topic.set-homestay-price-topic}",
            properties = {"spring.json.value.default.type=com.homestaywithme.consumer.model.SetHomestayPriceMessageWrapper"},
            concurrency = "1")
    public void consumeSetHomestayPrice(@Payload SetHomestayPriceMessageWrapper message) {
        for(var homestay: message.getPayload().getListSetHomestayPriceAvailability()) {
            for(int i = 1; i <= 365; i++) {
                var homestayAvailability = HomestayAvailability
                        .builder()
                        .homestayId(homestay.getHomestayId())
                        .date(homestay.getLatestDate().plusDays(i))
                        .price(homestay.getPrice())
                        .build();
                entityManager.persist(homestayAvailability);
            }
        }
    }
}
