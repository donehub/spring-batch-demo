package com.example.springbatchdemo.component.listener.job;

import com.example.springbatchdemo.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 16:30
 */
@Configuration
public class BatchProcessStudentCompletionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(BatchProcessStudentCompletionListener.class);

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (BatchStatus.STARTED.equals(jobExecution.getStatus())) {
            log.info("开始迁移学生信息: {} -> {}", "student_source", "student_target");
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {
            log.info("Job finished! Time to verify the results");

            int studentTargetCount = studentMapper.queryCount();
            log.info("学生信息迁移完毕, 共迁移数据: {} 条", studentTargetCount);
        }
    }
}
