package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Ticket;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 21:42
 */
@Configuration
public class TestFlatFileItemReaderStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "ticketFileItemReader")
    private FlatFileItemReader<Ticket> ticketFileItemReader;

    @Bean("testFlatFileItemReaderStep1")
    public Step testFlatFileItemReaderStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testFlatFileItemReaderStep1")
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(10)
                .reader(ticketFileItemReader)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
