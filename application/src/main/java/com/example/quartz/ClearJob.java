package com.example.quartz;

import com.example.service.ResourceCommentService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClearJob implements Job {
    @Autowired
    private ResourceCommentService commentService;

    @Override
    public void execute(JobExecutionContext context) {
        commentService.deleteOld();
    }
}
