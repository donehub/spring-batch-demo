package com.example.springbatchdemo.component.listener;

import com.example.springbatchdemo.entity.Person;
import com.example.springbatchdemo.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zourongsheng
 */
@Component
public class BatchProcessPersonCompletionListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(BatchProcessPersonCompletionListener.class);

    @Autowired
    private PersonMapper personMapper;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job finished! Time to verify the results");

            List<Person> personList = personMapper.queryAll();
            personList.forEach(person -> log.info("Found <{}> in the database.", person));
        }
    }
}
