package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.partitioner.TransferStudentPartitioner;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/19 18:18
 */
@Configuration
public class MasterTransferStudentStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "transferStudentPartitionHandler1")
    private PartitionHandler transferStudentPartitionHandler;

    @Autowired
    private TransferStudentPartitioner transferStudentPartitioner;

    @Bean("masterTransferStudentStep1")
    public Step masterTransferStudentStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("masterTransferStudentStep1.manager")
                .partitioner("masterTransferStudentStep1", transferStudentPartitioner)
                .partitionHandler(transferStudentPartitionHandler)
                .build();
    }
}
