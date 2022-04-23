package com.example.springbatchdemo;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试基类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractSpringTest {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

}
