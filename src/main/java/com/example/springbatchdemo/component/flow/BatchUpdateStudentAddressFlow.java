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
 * @date 2022/5/5 21:57
 */
@Configuration
public class BatchUpdateStudentAddressFlow {

    @Autowired
    @Qualifier(value = "batchUpdateStudentAddressStep1")
    private Step batchUpdateStudentAddressStep;

    @Bean("batchUpdateStudentAddressFlow1")
    public Flow batchUpdateStudentAddressFlow1() {
        return new FlowBuilder<SimpleFlow>("batchUpdateStudentAddressFlow1")
                .start(batchUpdateStudentAddressStep)
                .build();
    }
}
