package com.example.controller;

import com.example.dto.job.request.UpdateJobRequestDto;
import com.example.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "jobs", description = "Контроллер для работы с задачами Quartz")
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(description = "Изменить время исполнения задачи")
    @PatchMapping("/{job}")
    public void updateTriggerTime(
            @PathVariable String job,
            @RequestBody @Valid UpdateJobRequestDto dto) throws SchedulerException {
        jobService.rescheduleJob(job, Long.parseLong(dto.getTime()));
    }
}
