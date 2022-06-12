package com.example.springbatchdemo.component.listener.writer;

import com.example.springbatchdemo.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/12 18:46
 */
@Component
public class TestWriteListener implements ItemWriteListener<Ticket> {

    private static final Logger log = LoggerFactory.getLogger(TestWriteListener.class);

    @Override
    public void beforeWrite(List<? extends Ticket> items) {
        log.info("before write: {}", items);
    }

    @Override
    public void afterWrite(List<? extends Ticket> items) {
        log.info("after write: {}", items);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Ticket> items) {
        log.info("write item error: {}", exception.getMessage(), exception);
    }
}
