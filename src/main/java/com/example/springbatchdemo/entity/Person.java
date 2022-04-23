package com.example.springbatchdemo.entity;

import lombok.Data;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 15:21
 */
@Data
public class Person {

    private Long personId;

    private String firstName;

    private String lastName;

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }
}
