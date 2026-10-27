package com.plateform.multiplayer_platform.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration 
public class TaskSchedulerConfig {

    @Bean
    public TaskScheduler getScheduler()
    {
        ThreadPoolTaskScheduler tScheduler = new ThreadPoolTaskScheduler();

        tScheduler.setPoolSize(4);
        tScheduler.setThreadNamePrefix("match-scheduler-");

        return tScheduler;
    }

}
