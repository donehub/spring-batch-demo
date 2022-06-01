package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Ticket;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 22:54
 */
@Configuration
public class TestJsonItemReaderStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "ticketJsonItemReader")
    private JsonItemReader<Ticket> ticketJsonItemReader;

    @Bean("testJsonItemReaderStep1")
    public Step testJsonItemReaderStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testJsonItemReaderStep1")
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(10)
                .reader(ticketJsonItemReader)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
