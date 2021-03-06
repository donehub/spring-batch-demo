package com.example.springbatchdemo.component.processor;

import com.example.springbatchdemo.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/5 22:10
 */
@Configuration
public class AppendStudentAddressProcessor implements ItemProcessor<Student, Student> {

    private static final Logger log = LoggerFactory.getLogger(AppendStudentAddressProcessor.class);

    @Override
    public Student process(final Student studentSource) throws Exception {

        final Long studentId = studentSource.getStudentId();
        final String name = studentSource.getName();
        final String address = studentSource.getAddress();

        final Student studentTarget = new Student();
        studentTarget.setStudentId(studentId);
        studentTarget.setName(name);
        studentTarget.setAddress(address.concat("_8"));

        log.info("Converting ({}) into ({})", studentSource, studentTarget);

        return studentTarget;
    }
}
