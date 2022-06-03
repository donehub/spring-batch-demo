package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.entity.Ticket;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/3 19:35
 */
@Configuration
public class TestMultiFileItemWriterStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier(value = "ticketFileItemReader")
    private FlatFileItemReader<Ticket> ticketFileItemReader;

    @Autowired
    @Qualifier(value = "ticketClassifierMultiFileItemWriter")
    public ClassifierCompositeItemWriter<Ticket> ticketClassifierMultiFileItemWriter;

    @Autowired
    @Qualifier(value = "ticketFileItemWriter")
    public FlatFileItemWriter<Ticket> ticketFileItemWriter;

    @Autowired
    @Qualifier(value = "ticketXmlItemWriter")
    public StaxEventItemWriter<Ticket> ticketXmlItemWriter;

    @Bean("testMultiFileItemWriterStep1")
    public Step testMultiFileItemWriterStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testMultiFileItemWriterStep1")
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(10)
                .reader(ticketFileItemReader)
                .writer(ticketClassifierMultiFileItemWriter)
                .stream(ticketFileItemWriter)
                .stream(ticketXmlItemWriter)
                .build();
    }
}
