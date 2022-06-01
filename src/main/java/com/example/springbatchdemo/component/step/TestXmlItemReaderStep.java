package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Ticket;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 22:05
 */
@Configuration
public class TestXmlItemReaderStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "ticketXmlItemReader")
    private StaxEventItemReader<Ticket> ticketXmlItemReader;

    @Bean("testXmlItemReaderStep1")
    public Step testXmlItemReaderStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testXmlItemReaderStep1")
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(10)
                .reader(ticketXmlItemReader)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
