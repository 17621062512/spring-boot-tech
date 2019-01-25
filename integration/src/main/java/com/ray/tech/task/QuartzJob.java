package com.ray.tech.task;

import com.ray.tech.annotation.QuartzScheduled;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@QuartzScheduled("0/5 * * * * ? ")
public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Quartz定时任务");
    }

}
