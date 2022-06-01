package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Student;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 22:00
 */
@Configuration
public class TestDatabaseItemReaderStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "studentItemReader")
    private JdbcPagingItemReader<Student> studentItemReader;

    @Bean("testDatabaseItemReaderStep1")
    public Step testDatabaseItemReaderStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testDatabaseItemReaderStep1")
                .transactionManager(transactionManager)
                .<Student, Student>chunk(10)
                .reader(studentItemReader)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
