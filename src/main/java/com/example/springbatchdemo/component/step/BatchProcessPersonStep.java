package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.processor.PersonItemProcessor;
import com.example.springbatchdemo.entity.Person;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:34
 */
@Configuration
public class BatchProcessPersonStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "personItemReader")
    private FlatFileItemReader<Person> personItemReader;

    @Autowired
    @Qualifier(value = "personItemWriter")
    private JdbcBatchItemWriter<Person> personItemWriter;

    @Autowired
    private PersonItemProcessor personItemProcessor;

    @Bean("batchProcessPersonStep1")
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(personItemReader)
                .processor(personItemProcessor)
                .writer(personItemWriter)
                .build();
    }
}
