package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Ticket;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 23:03
 */
@Configuration
public class TestMultiFileItemReaderStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "ticketMultiFileItemReader")
    private MultiResourceItemReader<Ticket> ticketMultiFileItemReader;

    @Bean("testMultiFileItemReaderStep1")
    public Step testMultiFileItemReaderStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testMultiFileItemReaderStep1")
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(10)
                .reader(ticketMultiFileItemReader)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
