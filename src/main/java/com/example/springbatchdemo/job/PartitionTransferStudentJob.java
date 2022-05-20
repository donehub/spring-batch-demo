package com.example.springbatchdemo.job;

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
 * @date 2022/5/19 20:15
 */
@Configuration
@EnableBatchProcessing
public class PartitionTransferStudentJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "masterTransferStudentStep1")
    private Step masterTransferStudentStep;

    @Bean
    public Job partitionTransferStudentJob1() {
        return jobBuilderFactory.get("partitionTransferStudentJob1")
                .incrementer(new RunIdIncrementer())
                .flow(masterTransferStudentStep)
                .end()
                .build();
    }
}
