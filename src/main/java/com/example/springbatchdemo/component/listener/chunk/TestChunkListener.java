package com.example.springbatchdemo.component.listener.chunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/12 17:24
 */
@Component
public class TestChunkListener extends ChunkListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(TestChunkListener.class);

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("before chunk: {}", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("after chunk: {}", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.info("after chunk error: {}", context.getStepContext().getStepName());
    }
}
