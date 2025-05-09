package com.homestaywithme.application.job;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {
    private final Scheduler scheduler;

    @PostConstruct
    public void scheduleJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SetHomestayPriceJob.class)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                //.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 12 ? *"))
                //.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(60))
                .startNow()
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
