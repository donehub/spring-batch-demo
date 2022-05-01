package com.example.springbatchdemo.entity;

import lombok.Data;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/5/1 15:51
 */
@Data
public class Student {

    private Long studentId;

    private String name;

    private String address;

    @Override
    public String toString() {
        return "name: " + name + ", address: " + address;
    }
}
