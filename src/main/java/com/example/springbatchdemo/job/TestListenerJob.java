package com.example.springbatchdemo.job;

import com.example.springbatchdemo.component.listener.job.TestJobListener;
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
 * @date 2022/6/11 15:40
 */
@Configuration
@EnableBatchProcessing
public class TestListenerJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private TestJobListener testJobListener;

    @Autowired
    @Qualifier(value = "testListenerStep1")
    private Step testListenerStep;

    @Bean
    public Job testListenerJob1() {
        return jobBuilderFactory.get("testListenerJob1")
                .incrementer(new RunIdIncrementer())
                .listener(testJobListener)
                .flow(testListenerStep)
                .end()
                .build();
    }
}
