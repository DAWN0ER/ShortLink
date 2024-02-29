package com.dawn.shortlink.configuration;

import com.dawn.shortlink.domain.task.TimedCleanJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    private static final String ID = "TimedCleanExpired";
    private final String cron = "0 0 4 * * ? "; // 每天凌晨4点执行任务

    @Bean
    public JobDetail timedCleanExpiredJobDetail() {
        return JobBuilder.newJob(TimedCleanJob.class)
                .withIdentity(ID)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger timedCleanExpiredTrigger() {
        // Cron 调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        return TriggerBuilder.newTrigger()
                .forJob(timedCleanExpiredJobDetail())
                .withIdentity(ID + "_Trigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
