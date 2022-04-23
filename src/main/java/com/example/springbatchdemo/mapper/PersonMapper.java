package com.example.springbatchdemo.mapper;

import com.example.springbatchdemo.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 15:24
 */
@Mapper
public interface PersonMapper {

    Person get(@Param("personId") Long personId);

    int add(Person person);

    List<Person> queryAll();
}
