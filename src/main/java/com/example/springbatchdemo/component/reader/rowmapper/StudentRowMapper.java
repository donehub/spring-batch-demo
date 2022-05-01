package com.example.springbatchdemo.component.reader.rowmapper;

import com.example.springbatchdemo.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 16:08
 */
public class StudentRowMapper implements RowMapper<Student> {


    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

        Student student = new Student();
        student.setStudentId(rs.getLong("student_id"));
        student.setName(rs.getString("name"));
        student.setAddress(rs.getString("address"));
        return student;
    }
}
