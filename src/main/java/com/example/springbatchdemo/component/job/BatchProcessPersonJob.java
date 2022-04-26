package com.example.springbatchdemo.component.job;

import com.example.springbatchdemo.component.listener.BatchProcessPersonCompletionListener;
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
 * @date 2022/4/23 16:27
 */
@Configuration
@EnableBatchProcessing
public class BatchProcessPersonJob {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    @Qualifier(value = "batchProcessPersonStep1")
    private Step batchProcessPersonStep1;

    @Autowired
    private BatchProcessPersonCompletionListener batchProcessPersonCompletionListener;

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(batchProcessPersonCompletionListener)
                .flow(batchProcessPersonStep1)
                .end()
                .build();
    }
}
