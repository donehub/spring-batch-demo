package com.example.springbatchdemo.component.processor;

import com.example.springbatchdemo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/5 22:01
 */
@Configuration
public class AppendStudentNameOneProcessor implements ItemProcessor<Student, Student> {

    private static final Logger log = LoggerFactory.getLogger(AppendStudentNameOneProcessor.class);

    @Override
    public Student process(final Student studentSource) throws Exception {

        final Long studentId = studentSource.getStudentId();
        final String name = studentSource.getName();
        final String address = studentSource.getAddress();

        final Student studentTarget = new Student();
        studentTarget.setStudentId(studentId);
        studentTarget.setName(name.concat("_1"));
        studentTarget.setAddress(address);

        log.info("Converting ({}) into ({})", studentSource, studentTarget);

        return studentTarget;
    }
}
