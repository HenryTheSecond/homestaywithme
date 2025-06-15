package com.homestaywithme.application.job.sethomestaypricejob;

import com.homestaywithme.domain.booking.entity.HomestayAvailability;
import com.homestaywithme.domain.booking.repository.homestayavailability.HomestayAvailabilityRepository;
import com.homestaywithme.domain.booking.repository.homestayavailability.model.SetPriceHomestayAvailability;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class SetHomestayPriceJob implements Job {
    private static final int MAX_RESULT = 4;
    private static final int CHUNK_SIZE = 2;
    private static final int THREAD_COUNT = 4;

    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    private final Queue<List<SetPriceHomestayAvailability>> chunkQueue = new LinkedList<>();
    private Long lastHomestayId = null;
    private boolean isRemaining = true;

    private final LocalDate date;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public SetHomestayPriceJob(HomestayAvailabilityRepository homestayAvailabilityRepository,
                               EntityManagerFactory entityManagerFactory) {
        date = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        for(int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(this::handleJob);
        }
    }

    private void handleJob() {
        while(true) {
            var data = getDataFromQueue();
            if(data.isEmpty()) {
                break;
            }

            handleJobTransactional(data);
        }
    }

    private void handleJobTransactional(List<SetPriceHomestayAvailability> data) {
        var entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            for(var homestay: data) {
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

            entityManager.flush();
            transaction.commit();
        } catch (Exception e) {
            log.error("Exception occurs", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private synchronized List<SetPriceHomestayAvailability> getDataFromQueue() {
        if(!isRemaining) {
            return Collections.emptyList();
        }

        if(chunkQueue.isEmpty()) {
            var result = homestayAvailabilityRepository.getSetPriceHomestayAvailability(lastHomestayId,
                    date,
                    PageRequest.of(0, MAX_RESULT));

            if(result.isEmpty()) {
                isRemaining = false;
                return Collections.emptyList();
            }
            lastHomestayId = result.get(result.size() - 1).getHomestayId();
            refillQueue(result);
        }

        return chunkQueue.poll();
    }

    private void refillQueue(List<SetPriceHomestayAvailability> list) {
        List<SetPriceHomestayAvailability> chunk = new ArrayList<>();
        for(var data: list) {
            chunk.add(data);
            if(chunk.size() == CHUNK_SIZE) {
                chunkQueue.offer(chunk);
                chunk = new ArrayList<>();
            }
        }

        if(!chunk.isEmpty()) {
            chunkQueue.offer(chunk);
        }
    }
}
