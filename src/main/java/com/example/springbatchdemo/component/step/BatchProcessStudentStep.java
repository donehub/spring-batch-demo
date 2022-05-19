package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.processor.StudentItemProcessor;
import com.example.springbatchdemo.entity.Student;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.springbatchdemo.config.ExecutorConfig.TASK_EXECUTOR;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 16:23
 */
@Configuration
public class BatchProcessStudentStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "studentItemReader")
    private JdbcPagingItemReader<Student> studentItemReader;

    @Autowired
    @Qualifier(value = "studentItemWriter")
    private JdbcBatchItemWriter<Student> studentItemWriter;

    @Autowired
    private StudentItemProcessor studentItemProcessor;

    @Autowired
    @Qualifier(value = TASK_EXECUTOR)
    private ThreadPoolTaskExecutor taskExecutor;

    @Bean("batchTransferStudentStep1")
    public Step batchTransferStudentStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("batchTransferStudentStep1")
                .transactionManager(transactionManager)
                .<Student, Student>chunk(1000)
                .reader(studentItemReader)
                .processor(studentItemProcessor)
                .writer(studentItemWriter)
                .taskExecutor(taskExecutor)
                .throttleLimit(30)
                .build();
    }
}
