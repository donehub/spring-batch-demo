package com.example.springbatchdemo.component.listener.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/11 20:54
 */
@Component
public class TestJobListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TestJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("before job: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("after job: {}", jobExecution.getJobInstance().getJobName());
    }
}
