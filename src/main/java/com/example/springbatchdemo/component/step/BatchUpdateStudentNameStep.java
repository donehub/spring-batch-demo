package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.processor.AppendStudentNameOneProcessor;
import com.example.springbatchdemo.component.processor.AppendStudentNameTwoProcessor;
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
 * @date 2022/5/5 21:59
 */
@Configuration
public class BatchUpdateStudentNameStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "studentItemReader")
    private JdbcPagingItemReader<Student> studentItemReader;

    @Autowired
    @Qualifier(value = "studentItemUpdateName")
    private JdbcBatchItemWriter<Student> studentItemUpdateName;

    @Autowired
    private AppendStudentNameOneProcessor appendStudentNameOneProcessor;

    @Autowired
    private AppendStudentNameTwoProcessor appendStudentNameTwoProcessor;

    @Autowired
    @Qualifier(value = TASK_EXECUTOR)
    private ThreadPoolTaskExecutor taskExecutor;

    @Bean("batchUpdateStudentNameStep1")
    public Step batchUpdateStudentNameStep1() {
        return stepBuilderFactory.get("batchUpdateStudentNameStep1")
                .<Student, Student>chunk(1000)
                .reader(studentItemReader)
                .processor(appendStudentNameOneProcessor)
                .writer(studentItemUpdateName)
                .taskExecutor(taskExecutor)
                .throttleLimit(30)
                .build();
    }

    @Bean("batchUpdateStudentNameStep2")
    public Step batchUpdateStudentNameStep2() {
        return stepBuilderFactory.get("batchUpdateStudentNameStep2")
                .<Student, Student>chunk(10)
                .reader(studentItemReader)
                .processor(appendStudentNameTwoProcessor)
                .writer(studentItemUpdateName)
                .taskExecutor(taskExecutor)
                .throttleLimit(30)
                .build();
    }
}
