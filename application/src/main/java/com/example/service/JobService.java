package com.example.service;

import com.example.exception.QuartzNoEntityException;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobService {

    private final Scheduler scheduler;

    private final String DEFAULT_GROUP_NAME = "DEFAULT";

    public void rescheduleJob(String jobName, long time) throws SchedulerException {
        JobKey jobKey = scheduler.getJobKeys(GroupMatcher.groupEquals(DEFAULT_GROUP_NAME)).stream()
                .filter(key -> key.getName().equals(jobName))
                .findAny()
                .orElseThrow(() -> new QuartzNoEntityException("Задачи с таким названием не существует"));

        List<TriggerKey> triggerKeys = scheduler.getTriggersOfJob(jobKey).stream()
                .map(Trigger::getKey)
                .toList();

        for (TriggerKey triggerKey : triggerKeys) {
            Trigger trigger = scheduler.getTrigger(triggerKey);

            SimpleTriggerImpl sti = new SimpleTriggerImpl();

            sti.setName(jobName);
            sti.setGroup(DEFAULT_GROUP_NAME);
            sti.setJobKey(jobKey);
            sti.setJobDataMap(trigger.getJobDataMap());
            sti.setStartTime(trigger.getStartTime());
            sti.setRepeatInterval(time);
            sti.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
            sti.setPriority(trigger.getPriority());
            sti.setMisfireInstruction(trigger.getMisfireInstruction());
            sti.setDescription(trigger.getDescription());

            scheduler.rescheduleJob(triggerKey, sti);
        }
    }
}
