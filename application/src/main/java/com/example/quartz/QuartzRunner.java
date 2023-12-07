package com.example.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class QuartzRunner implements ApplicationRunner {

    private final String CLEAR_JOB_NAME = "clear";
    private final String CLEAR_TRIGGER_NAME = "clear";
    private final long DEFAULT_TRIGGER_FREQUENCY = 900000;

    private final Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDetail job = createJobDetail(ClearJob.class, CLEAR_JOB_NAME);

        Trigger trigger = createTrigger(job, DEFAULT_TRIGGER_FREQUENCY, CLEAR_TRIGGER_NAME);

        scheduler.scheduleJob(job, trigger);
    }

    private Trigger createTrigger(JobDetail jobDetail, long pollFrequencyMs, String triggerName) {
        SimpleTriggerImpl sti = new SimpleTriggerImpl();

        sti.setJobKey(jobDetail.getKey());
        sti.setStartTime(new Date());
        sti.setRepeatInterval(pollFrequencyMs);
        sti.setName(triggerName);
        sti.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        sti.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        return sti;
    }

    private JobDetail createJobDetail(Class<? extends Job> jobClass, String jobName) {
        JobDetailImpl factoryBean = new JobDetailImpl();

        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);

        return factoryBean;
    }
}
