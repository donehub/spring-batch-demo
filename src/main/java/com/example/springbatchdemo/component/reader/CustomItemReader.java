package com.example.springbatchdemo.component.reader;

import com.example.springbatchdemo.component.reader.rowmapper.StudentRowMapper;
import com.example.springbatchdemo.entity.Person;
import com.example.springbatchdemo.entity.Student;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:14
 */
@Configuration
public class CustomItemReader {

    @Autowired
    @Qualifier(value = "batchDemoDB")
    private DataSource batchDemoDB;

    @Bean("personItemReader")
    public FlatFileItemReader<Person> personItemReader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean("studentItemReader")
    public JdbcPagingItemReader<Student> studentItemReader() {

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("student_id, name, address");
        queryProvider.setFromClause("from student_source");
        queryProvider.setWhereClause("where student_id >= 0");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("student_id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return new JdbcPagingItemReaderBuilder<Student>()
                .name("studentItemReader")
                .dataSource(batchDemoDB)
                .fetchSize(1000)
                .rowMapper(new StudentRowMapper())
                .queryProvider(queryProvider)
                .build();
    }
}
