package com.example.quartz;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {
    private final ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();

        jobFactory.setApplicationContext(applicationContext);

        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        if (ArrayUtils.isNotEmpty(triggers)) {
            schedulerFactory.setTriggers(triggers);
        }

        return schedulerFactory;
    }
}