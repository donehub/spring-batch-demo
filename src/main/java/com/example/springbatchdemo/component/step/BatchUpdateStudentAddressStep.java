package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.processor.AppendStudentAddressProcessor;
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

import static com.example.springbatchdemo.config.ExecutorConfig.TASK_EXECUTOR;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/5 22:00
 */
@Configuration
public class BatchUpdateStudentAddressStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "studentItemReader")
    private JdbcPagingItemReader<Student> studentItemReader;

    @Autowired
    @Qualifier(value = "studentItemUpdateAddress")
    private JdbcBatchItemWriter<Student> studentItemUpdateAddress;

    @Autowired
    private AppendStudentAddressProcessor appendStudentAddressProcessor;

    @Autowired
    @Qualifier(value = TASK_EXECUTOR)
    private ThreadPoolTaskExecutor taskExecutor;

    @Bean("batchUpdateStudentAddressStep1")
    public Step batchUpdateStudentAddressStep1() {
        return stepBuilderFactory.get("batchUpdateStudentAddressStep1")
                .<Student, Student>chunk(1000)
                .reader(studentItemReader)
                .processor(appendStudentAddressProcessor)
                .writer(studentItemUpdateAddress)
                .taskExecutor(taskExecutor)
                .throttleLimit(30)
                .build();
    }
}
