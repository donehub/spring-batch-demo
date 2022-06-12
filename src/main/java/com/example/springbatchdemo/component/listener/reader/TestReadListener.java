package com.example.springbatchdemo.component.listener.reader;

import com.example.springbatchdemo.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/12 18:45
 */
@Component
public class TestReadListener implements ItemReadListener<Ticket> {

    private static final Logger log = LoggerFactory.getLogger(TestReadListener.class);

    @Override
    public void beforeRead() {
        log.info("before read");
    }

    @Override
    public void afterRead(Ticket item) {
        log.info("after read: {}", item);
    }

    @Override
    public void onReadError(Exception ex) {
        log.info("read item error: {}", ex.getMessage(), ex);
    }
}
