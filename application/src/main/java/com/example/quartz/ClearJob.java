package com.example.quartz;

import com.example.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@DisallowConcurrentExecution
public class ClearJob implements Job {
    @Autowired
    private CommentService commentService;

    @Override
    public void execute(JobExecutionContext context) {
        log.info("Job ** {} ** executed @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        commentService.deleteOld();
    }
}
