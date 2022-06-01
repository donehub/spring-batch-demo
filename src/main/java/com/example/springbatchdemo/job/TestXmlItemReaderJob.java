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
 * @date 2022/6/1 22:04
 */
@Configuration
@EnableBatchProcessing
public class TestXmlItemReaderJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "testXmlItemReaderStep1")
    private Step testXmlItemReaderStep;

    @Bean
    public Job testXmlItemReaderJob1() {
        return jobBuilderFactory.get("testXmlItemReaderJob1")
                .incrementer(new RunIdIncrementer())
                .flow(testXmlItemReaderStep)
                .end()
                .build();
    }
}
