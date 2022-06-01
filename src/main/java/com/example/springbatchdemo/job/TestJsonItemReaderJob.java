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
 * @date 2022/6/1 22:54
 */
@Configuration
@EnableBatchProcessing
public class TestJsonItemReaderJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "testJsonItemReaderStep1")
    private Step testJsonItemReaderStep;

    @Bean
    public Job testJsonItemReaderJob1() {
        return jobBuilderFactory.get("testJsonItemReaderJob1")
                .incrementer(new RunIdIncrementer())
                .flow(testJsonItemReaderStep)
                .end()
                .build();
    }
}
