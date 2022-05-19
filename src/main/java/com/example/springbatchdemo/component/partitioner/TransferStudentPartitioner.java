package com.example.springbatchdemo.component.partitioner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/18 9:47
 */
@Configuration
public class TransferStudentPartitioner implements Partitioner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferStudentPartitioner.class);

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> result = new HashMap<>(gridSize);

        int range = 1000;
        int fromId = 0;
        int toId = range;

        for (int i = 0; i < gridSize; i++) {

            ExecutionContext value = new ExecutionContext();

            value.putInt("fromId", fromId);
            value.putInt("toId", toId);

            result.put("partition" + i, value);

            fromId = toId;
            toId += range;

            LOGGER.info("partition{}; fromId: {}; toId: {}", i, fromId, toId);
        }

        return result;
    }
}
