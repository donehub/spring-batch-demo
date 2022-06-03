package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Student;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/3 17:42
 */
@Configuration
public class TestDatabaseItemWriterStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "studentFileItemReader")
    private FlatFileItemReader<Student> studentFileItemReader;

    @Autowired
    @Qualifier(value = "studentItemWriter")
    public JdbcBatchItemWriter<Student> studentItemWriter;

    @Bean("testDatabaseItemWriterStep1")
    public Step testDatabaseItemWriterStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testDatabaseItemWriterStep1")
                .transactionManager(transactionManager)
                .<Student, Student>chunk(10)
                .reader(studentFileItemReader)
                .writer(studentItemWriter)
                .build();
    }
}
