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

    private final String CLEAR_JOB_NAME = "clear";
    private final String CLEAR_TRIGGER_NAME = "clear";
    private final long DEFAULT_TRIGGER_FREQUENCY = 900000;

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

    @Bean
    public JobDetailFactoryBean clear() {
        return createJobDetail(
                ClearJob.class,
                CLEAR_JOB_NAME
        );
    }

    @Bean
    public SimpleTriggerFactoryBean triggerClear(JobDetail jobDetail) {
        return createTrigger(
                jobDetail,
                DEFAULT_TRIGGER_FREQUENCY,
                CLEAR_TRIGGER_NAME
        );
    }

    private SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs, String triggerName) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();

        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setName(triggerName);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        return factoryBean;
    }

    private JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass, String jobName) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();

        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);

        return factoryBean;
    }
}