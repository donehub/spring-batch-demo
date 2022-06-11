package com.example.springbatchdemo.component.listener.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/11 21:02
 */
@Component
public class TestStepListener extends StepExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TestStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("before step: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("after step: {}", stepExecution.getStepName());
        return null;
    }
}
