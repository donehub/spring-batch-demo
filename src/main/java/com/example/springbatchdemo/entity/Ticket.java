package com.example.springbatchdemo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 21:36
 */
@Data
public class Ticket {

    /**
     * 始发站
     */
    private String departureStation;

    /**
     * 到达站
     */
    private String arrivalStation;

    /**
     * 票价
     */
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("始发站: %s; 到达站: %s; 票价: %s", departureStation, arrivalStation, price);
    }
}
