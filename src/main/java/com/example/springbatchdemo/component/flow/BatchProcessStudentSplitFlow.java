package com.example.springbatchdemo.component.flow;

import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.example.springbatchdemo.config.ExecutorConfig.TASK_EXECUTOR;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/5 21:55
 */
@Configuration
public class BatchProcessStudentSplitFlow {

    @Autowired
    @Qualifier(value = TASK_EXECUTOR)
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    @Qualifier(value = "updateStudentNameOneAndTwoFlow")
    private Flow updateStudentNameOneAndTwoFlow;

    @Autowired
    @Qualifier(value = "batchUpdateStudentAddressFlow1")
    private Flow batchUpdateStudentAddressFlow;

    @Bean("batchProcessStudentSplitFlow1")
    public Flow batchProcessStudentSplitFlow1() {
        return new FlowBuilder<SimpleFlow>("batchProcessStudentSplitFlow1")
                .split(taskExecutor)
                .add(updateStudentNameOneAndTwoFlow, batchUpdateStudentAddressFlow)
                .build();
    }
}
