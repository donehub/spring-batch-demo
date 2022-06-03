package com.example.springbatchdemo.component.reader;

import com.example.springbatchdemo.component.reader.rowmapper.StudentRowMapper;
import com.example.springbatchdemo.entity.Person;
import com.example.springbatchdemo.entity.Student;
import com.example.springbatchdemo.entity.Ticket;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.xstream.XStreamMarshaller;

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

    @Autowired
    @Qualifier(value = "ticketMarshaller")
    private XStreamMarshaller ticketMarshaller;

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
    @StepScope
    public JdbcPagingItemReader<Student> studentItemReader() {

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("student_id, name, address");
        queryProvider.setFromClause("from student_source");

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

    @Bean("slaveTransferStudentItemReader")
    @StepScope
    public JdbcPagingItemReader<Student> slaveTransferStudentItemReader(@Value("#{stepExecutionContext[fromId]}") final Long fromId,
                                                                        @Value("#{stepExecutionContext[toId]}") final Long toId) {

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("student_id, name, address");
        queryProvider.setFromClause("from student_source");
        queryProvider.setWhereClause(String.format("where student_id > %s and student_id <= %s", fromId, toId));

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

    @Bean("ticketFileItemReader")
    public FlatFileItemReader<Ticket> ticketFileItemReader() {
        return new FlatFileItemReaderBuilder<Ticket>()
                .name("ticketFileItemReader")
                .resource(new ClassPathResource("ticket.csv"))
                .delimited()
                .names(new String[]{"departureStation", "arrivalStation", "price"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Ticket>() {{
                    setTargetType(Ticket.class);
                }})
                .build();
    }

    @Bean("ticketXmlItemReader")
    public StaxEventItemReader<Ticket> itemReader() {
        return new StaxEventItemReaderBuilder<Ticket>()
                .name("ticketXmlItemReader")
                .resource(new ClassPathResource("ticket.xml"))
                .addFragmentRootElements("ticket")
                .unmarshaller(ticketMarshaller)
                .build();

    }

    @Bean("ticketJsonItemReader")
    public JsonItemReader<Ticket> ticketJsonItemReader() {
        return new JsonItemReaderBuilder<Ticket>()
                .name("ticketJsonItemReader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(Ticket.class))
                .resource(new ClassPathResource("ticket.json"))
                .build();
    }

    @Bean("commonTicketFileItemReader")
    public FlatFileItemReader<Ticket> commonTicketFileItemReader() {
        return new FlatFileItemReaderBuilder<Ticket>()
                .name("commonTicketFileItemReader")
                .delimited()
                .names(new String[]{"departureStation", "arrivalStation", "price"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Ticket>() {{
                    setTargetType(Ticket.class);
                }})
                .build();
    }

    @Bean("ticketMultiFileItemReader")
    public MultiResourceItemReader<Ticket> ticketMultiFileItemReader() {

        Resource[] resources = new Resource[]{
                new ClassPathResource("ticket-1.csv"),
                new ClassPathResource("ticket-2.csv")};

        return new MultiResourceItemReaderBuilder<Ticket>()
                .name("ticketMultiFileItemReader")
                .delegate(commonTicketFileItemReader())
                .resources(resources)
                .build();
    }

    @Bean("studentFileItemReader")
    public FlatFileItemReader<Student> studentFileItemReader() {
        return new FlatFileItemReaderBuilder<Student>()
                .name("ticketFileItemReader")
                .resource(new ClassPathResource("student.csv"))
                .delimited()
                .names(new String[]{"studentId", "name", "address"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
                    setTargetType(Student.class);
                }})
                .build();
    }
}
