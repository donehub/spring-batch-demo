package com.example.springbatchdemo.component.listener.processor;

import com.example.springbatchdemo.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/12 18:46
 */
@Component
public class TestProcessListener implements ItemProcessListener<Ticket, Ticket> {

    private static final Logger log = LoggerFactory.getLogger(TestProcessListener.class);

    @Override
    public void beforeProcess(Ticket item) {
        log.info("before process: {}", item);

    }

    @Override
    public void afterProcess(Ticket item, Ticket result) {
        log.info("after process: {}", item);
    }

    @Override
    public void onProcessError(Ticket item, Exception e) {
        log.info("process: {} error: {}", item, e.getMessage(), e);
    }
}
