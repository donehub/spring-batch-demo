package com.example.springbatchdemo.job;

import com.example.springbatchdemo.component.listener.BatchProcessStudentCompletionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 16:21
 */
@Configuration
@EnableBatchProcessing
public class BatchProcessStudentJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "batchTransferStudentStep1")
    private Step batchTransferStudentStep1;

    @Autowired
    private BatchProcessStudentCompletionListener batchProcessStudentCompletionListener;

    @Bean
    public Job transferStudentJob() {
        return jobBuilderFactory.get("transferStudentJob")
                .incrementer(new RunIdIncrementer())
                .listener(batchProcessStudentCompletionListener)
                .flow(batchTransferStudentStep1)
                .end()
                .build();
    }
}
