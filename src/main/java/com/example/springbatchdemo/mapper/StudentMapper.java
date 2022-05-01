package com.example.springbatchdemo.mapper;

import com.example.springbatchdemo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 16:31
 */
@Mapper
public interface StudentMapper {

    int batchInsert(@Param("studentList") List<Student> studentList);

    List<Student> queryAll();

    int queryCount();
}
