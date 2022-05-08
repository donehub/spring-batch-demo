package com.example.springbatchdemo.component.writer;

import com.example.springbatchdemo.entity.Person;
import com.example.springbatchdemo.entity.Student;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:15
 */
@Configuration
public class CustomItemWriter {

    @Autowired
    @Qualifier(value = "batchDemoDB")
    private DataSource batchDemoDB;

    @Bean("personItemWriter")
    public JdbcBatchItemWriter<Person> personItemWriter() {

        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO person (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("studentItemWriter")
    public JdbcBatchItemWriter<Student> studentItemWriter() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO student_target (student_id, name, address) VALUES (:studentId, :name, :address)")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("studentItemUpdateName")
    @StepScope
    public JdbcBatchItemWriter<Student> studentItemUpdateName() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE student_source SET name = :name WHERE student_id = :studentId")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("studentItemUpdateAddress")
    public JdbcBatchItemWriter<Student> studentItemUpdateAddress() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE student_source SET address = :address WHERE student_id = :studentId")
                .dataSource(batchDemoDB)
                .build();
    }
}
