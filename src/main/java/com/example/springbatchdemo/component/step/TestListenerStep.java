package com.example.springbatchdemo.component.step;

import com.example.springbatchdemo.component.listener.step.TestStepListener;
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
    @Qualifier(value = "ticketFileItemReader")
    private FlatFileItemReader<Ticket> ticketFileItemReader;

    @Bean("testListenerStep1")
    public Step testListenerStep1(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("testListenerStep1")
                .listener(testStepListener)
                .transactionManager(transactionManager)
                .<Ticket, Ticket>chunk(2)
                .reader(ticketFileItemReader)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
