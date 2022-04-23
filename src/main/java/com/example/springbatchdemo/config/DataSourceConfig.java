package com.example.springbatchdemo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:49
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "batchDemoDB")
    @ConfigurationProperties(prefix = "spring.datasource.batch-demo")
    public DataSource druidDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
