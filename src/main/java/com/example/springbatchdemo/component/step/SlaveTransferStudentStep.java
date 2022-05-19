package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.processor.SlaveStudentItemProcessor;
import com.example.springbatchdemo.entity.Student;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/19 20:02
 */
@Configuration
public class SlaveTransferStudentStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "slaveTransferStudentItemReader")
    private JdbcPagingItemReader<Student> slaveTransferStudentItemReader;

    @Autowired
    @Qualifier(value = "slaveTransferStudentItemWriter")
    private JdbcBatchItemWriter<Student> slaveTransferStudentItemWriter;

    @Autowired
    private SlaveStudentItemProcessor slaveStudentItemProcessor;


    @Bean("slaveTransferStudentStep1")
    public Step slaveTransferStudentStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("slaveTransferStudentStep1")
                .transactionManager(transactionManager)
                .<Student, Student>chunk(1000)
                .reader(slaveTransferStudentItemReader)
                .processor(slaveStudentItemProcessor)
                .writer(slaveTransferStudentItemWriter)
                .build();
    }
}
