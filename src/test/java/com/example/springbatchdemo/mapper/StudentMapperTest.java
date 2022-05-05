package com.example.springbatchdemo.mapper;

import com.example.springbatchdemo.AbstractSpringTest;
import com.example.springbatchdemo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 17:22
 */
class StudentMapperTest extends AbstractSpringTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void add() {

        int buffer = 100;
        List<Student> studentList = new ArrayList<>(buffer);
        int start = 0;

        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            int index = i + 1;
            student.setName("张三" + index);
            student.setAddress("上海市" + index);
            studentList.add(student);
            start++;
            if (start == buffer) {
                studentMapper.batchInsert(studentList);
                start = 0;
                studentList = new ArrayList<>(buffer);
            }
        }
    }

    @Test
    void queryCount() {
    }
}
