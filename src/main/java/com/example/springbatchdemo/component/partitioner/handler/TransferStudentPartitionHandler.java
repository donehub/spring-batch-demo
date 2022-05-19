package com.example.springbatchdemo.component.partitioner.handler;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.example.springbatchdemo.config.ExecutorConfig.TASK_EXECUTOR;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/19 16:34
 */
@Configuration
public class TransferStudentPartitionHandler {

    @Autowired
    @Qualifier(value = TASK_EXECUTOR)
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    @Qualifier(value = "slaveTransferStudentStep1")
    private Step slaveTransferStudentStep;

    @Bean("transferStudentPartitionHandler1")
    public PartitionHandler transferStudentPartitionHandler1() {
        TaskExecutorPartitionHandler retVal = new TaskExecutorPartitionHandler();
        retVal.setTaskExecutor(taskExecutor);
        retVal.setStep(slaveTransferStudentStep);
        retVal.setGridSize(100);
        return retVal;
    }
}
