package com.example.springbatchdemo.mapper;

import com.example.springbatchdemo.AbstractSpringTest;
import com.example.springbatchdemo.entity.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 17:23
 */
public class PersonMapperTest extends AbstractSpringTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void get() {
        Person person = personMapper.get(1L);
        log.info("person: {}", person);
    }

    @Test
    public void add() {
        Person person = new Person();
        person.setFirstName("张");
        person.setLastName("三");
        int addCnt = personMapper.add(person);
        log.info("addCnt: {}", addCnt);
    }

    @Test
    public void queryAll() {
        List<Person> personList = personMapper.queryAll();
        personList.forEach(person -> log.info("person: {}", person));
    }
}
