package com.homestaywithme.app.application.job.sethomestaypricejob;

import com.homestaywithme.app.application.kafka.producer.sethomestayprice.SetHomestayPriceMessage;
import com.homestaywithme.app.application.kafka.producer.sethomestayprice.SetHomestayPriceProducer;
import com.homestaywithme.app.domain.booking.repository.homestayavailability.HomestayAvailabilityRepository;
import com.homestaywithme.app.domain.booking.repository.homestayavailability.model.SetPriceHomestayAvailability;
import com.homestaywithme.share.kafka.KafkaMessage;
import com.homestaywithme.share.kafka.Meta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SetHomestayPriceJobV2 implements Job {
    private static final int MAX_RESULT = 4;
    private static final int CHUNK_SIZE = 2;

    private final HomestayAvailabilityRepository homestayAvailabilityRepository;
    private final SetHomestayPriceProducer setHomestayPriceProducer;

    @Override
    @Transactional(readOnly = true)
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var date = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        Long lastHomestayId = null;
        var pageRequest = PageRequest.of(0, MAX_RESULT);

        while(true) {
            var result = homestayAvailabilityRepository.getSetPriceHomestayAvailability(lastHomestayId,
                    date,
                    pageRequest);

            if(result.isEmpty()) {
                break;
            }

            lastHomestayId = result.get(result.size() - 1).getHomestayId();
            sendMessages(result);
        }
    }

    private void sendMessages(List<SetPriceHomestayAvailability> homestayPrices) {
        var message = new SetHomestayPriceMessage();
        for(var homestayPrice: homestayPrices) {
            message.getListSetHomestayPriceAvailability().add(homestayPrice);
            if(message.getListSetHomestayPriceAvailability().size() == CHUNK_SIZE) {
                setHomestayPriceProducer.send(new KafkaMessage<>(new Meta(UUID.randomUUID().toString(), "homestaywithme", Instant.now().toEpochMilli()),
                        message));
                message = new SetHomestayPriceMessage();
            }
        }

        if(!message.getListSetHomestayPriceAvailability().isEmpty()) {
            setHomestayPriceProducer.send(new KafkaMessage<>(new Meta(UUID.randomUUID().toString(), "homestaywithme", Instant.now().toEpochMilli()), message));
        }
    }
}
