package com.example.springbatchdemo.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/5 21:44
 */
@Configuration
@EnableBatchProcessing
public class ParallelManageStudentJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "batchProcessStudentSplitFlow1")
    private Flow batchProcessStudentSplitFlow;

    @Autowired
    @Qualifier(value = "batchTransferStudentStep1")
    private Step batchTransferStudentStep;

    @Bean
    public Job parallelManageStudentJob1() {
        return jobBuilderFactory.get("parallelManageStudentJob1")
                .incrementer(new RunIdIncrementer())
                .start(batchProcessStudentSplitFlow)
                .next(batchTransferStudentStep)
                .end()
                .build();
    }
}
