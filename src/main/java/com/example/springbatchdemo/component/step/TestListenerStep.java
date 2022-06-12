package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.listener.chunk.TestChunkListener;
import com.example.springbatchdemo.component.listener.processor.TestProcessListener;
import com.example.springbatchdemo.component.listener.reader.TestReadListener;
import com.example.springbatchdemo.component.listener.step.TestStepListener;
import com.example.springbatchdemo.component.listener.writer.TestWriteListener;
import com.example.springbatchdemo.component.processor.TicketItemProcessor;
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
 * @date 2022/6/11 20:52
 */
@Configuration
public class TestListenerStep {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private TestStepListener testStepListener;

    @Autowired
    private TestChunkListener testChunkListener;

    @Autowired
    private TestReadListener testReadListener;

    @Autowired
    private TestProcessListener testProcessListener;

    @Autowired
    private TestWriteListener testWriteListener;

    @Autowired
    @Qualifier(value = "ticketFileItemReader")
    private FlatFileItemReader<Ticket> ticketFileItemReader;

    @Autowired
    private TicketItemProcessor ticketItemProcessor;

    @Bean("testListenerStep1")
    public Step testListenerStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testListenerStep1")
                .listener(testStepListener)
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(2)
                .faultTolerant()
                .listener(testChunkListener)
                .reader(ticketFileItemReader)
                .listener(testReadListener)
                .processor(ticketItemProcessor)
                .listener(testProcessListener)
                .writer(list -> list.forEach(System.out::println))
                .listener(testWriteListener)
                .build();
    }
}
