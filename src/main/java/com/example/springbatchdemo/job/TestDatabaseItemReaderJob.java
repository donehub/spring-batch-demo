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
 * @date 2022/6/1 21:58
 */
@Configuration
@EnableBatchProcessing
public class TestDatabaseItemReaderJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "testDatabaseItemReaderStep1")
    private Step testDatabaseItemReaderStep;

    @Bean
    public Job testDatabaseItemReaderJob1() {
        return jobBuilderFactory.get("testDatabaseItemReaderJob1")
                .incrementer(new RunIdIncrementer())
                .flow(testDatabaseItemReaderStep)
                .end()
                .build();
    }
}
