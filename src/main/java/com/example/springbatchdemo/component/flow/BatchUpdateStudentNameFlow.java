package com.example.springbatchdemo.component.flow;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/5 21:56
 */
@Configuration
public class BatchUpdateStudentNameFlow {

    @Autowired
    @Qualifier(value = "batchUpdateStudentNameStep1")
    private Step batchUpdateStudentNameStep1;

    @Autowired
    @Qualifier(value = "batchUpdateStudentNameStep2")
    private Step batchUpdateStudentNameStep2;

    @Bean("batchUpdateStudentNameOneAndTwoFlow")
    public Flow updateStudentNameOneAndTwoFlow() {
        return new FlowBuilder<SimpleFlow>("batchUpdateStudentNameOneAndTwoFlow")
                .start(batchUpdateStudentNameStep1)
                .next(batchUpdateStudentNameStep2)
                .build();
    }
}
