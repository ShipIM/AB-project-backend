package com.example.controller;

import com.example.dto.job.request.UpdateJobRequestDto;
import com.example.exception.QuartzNoEntityException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "jobs", description = "Контроллер для работы с задачами Quartz")
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final Scheduler scheduler;

    private final String DEFAULT_GROUP_NAME = "DEFAULT";

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Изменить время исполнения задачи")
    @PatchMapping("/{job}")
    public void updateTriggerTime(
            @PathVariable String job,
            @RequestBody @Valid UpdateJobRequestDto dto) throws SchedulerException {
        JobKey jobKey = scheduler.getJobKeys(GroupMatcher.groupEquals(DEFAULT_GROUP_NAME)).stream()
                .filter(key -> key.getName().equals(job))
                .findAny()
                .orElseThrow(() -> new QuartzNoEntityException("Задачи с таким названием не существует"));

        List<TriggerKey> triggerKeys = scheduler.getTriggersOfJob(jobKey).stream()
                .map(Trigger::getKey)
                .toList();

        for (TriggerKey triggerKey : triggerKeys) {
            Trigger trigger = scheduler.getTrigger(triggerKey);

            SimpleTriggerImpl sti = new SimpleTriggerImpl();

            sti.setName(job);
            sti.setGroup(DEFAULT_GROUP_NAME);
            sti.setJobKey(jobKey);
            sti.setJobDataMap(trigger.getJobDataMap());
            sti.setStartTime(trigger.getStartTime());
            sti.setRepeatInterval(Long.parseLong(dto.getTime()));
            sti.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
            sti.setPriority(trigger.getPriority());
            sti.setMisfireInstruction(trigger.getMisfireInstruction());
            sti.setDescription(trigger.getDescription());

            scheduler.rescheduleJob(triggerKey, sti);
        }
    }
}
