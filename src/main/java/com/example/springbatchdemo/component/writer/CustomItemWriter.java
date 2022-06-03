package com.example.springbatchdemo.component.writer;

import com.example.springbatchdemo.entity.Person;
import com.example.springbatchdemo.entity.Student;
import com.example.springbatchdemo.entity.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:15
 */
@Configuration
public class CustomItemWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemWriter.class);

    @Autowired
    @Qualifier(value = "batchDemoDB")
    private DataSource batchDemoDB;

    @Autowired
    @Qualifier(value = "ticketMarshaller")
    private XStreamMarshaller ticketMarshaller;

    @Bean("personItemWriter")
    public JdbcBatchItemWriter<Person> personItemWriter() {

        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO person (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("studentItemWriter")
    public JdbcBatchItemWriter<Student> studentItemWriter() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO student_target (student_id, name, address) VALUES (:studentId, :name, :address)")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("studentItemUpdateName")
    @StepScope
    public JdbcBatchItemWriter<Student> studentItemUpdateName() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE student_source SET name = :name WHERE student_id = :studentId")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("studentItemUpdateAddress")
    public JdbcBatchItemWriter<Student> studentItemUpdateAddress() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("UPDATE student_source SET address = :address WHERE student_id = :studentId")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("slaveTransferStudentItemWriter")
    @StepScope
    public JdbcBatchItemWriter<Student> slaveTransferStudentItemWriter() {

        return new JdbcBatchItemWriterBuilder<Student>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO student_target (student_id, name, address) VALUES (:studentId, :name, :address)")
                .dataSource(batchDemoDB)
                .build();
    }

    @Bean("ticketFileItemWriter")
    public FlatFileItemWriter<Ticket> ticketFileItemWriter() {

        LineAggregator<Ticket> aggregator = item -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(item);
            } catch (JsonProcessingException e) {
                LOGGER.error("parse object to json error: {}", e.getMessage(), e);
            }
            return "";
        };

        // 分隔符拼接 POJO 字段值
//        BeanWrapperFieldExtractor<Ticket> extractor = new BeanWrapperFieldExtractor<>();
//        extractor.setNames(new String[]{"departureStation", "arrivalStation", "price"});
//
//        DelimitedLineAggregator<Ticket> aggregator1 = new DelimitedLineAggregator<>();
//        aggregator1.setFieldExtractor(extractor);
//        aggregator1.setDelimiter("|");

        return new FlatFileItemWriterBuilder<Ticket>()
                .name("ticketFileItemWriter")
                .resource(new FileSystemResource("ticket_output.txt"))
                .lineAggregator(aggregator)
                .build();
    }

    @Bean("ticketXmlItemWriter")
    public StaxEventItemWriter<Ticket> ticketXmlItemWriter() {
        return new StaxEventItemWriterBuilder<Ticket>()
                .name("ticketXmlItemWriter")
                .marshaller(ticketMarshaller)
                .resource(new FileSystemResource("ticket_output.xml"))
                .rootTagName("tickets")
                .overwriteOutput(true)
                .build();

    }

    @Bean("ticketJsonItemWriter")
    public JsonFileItemWriter<Ticket> ticketJsonItemWriter() {
        return new JsonFileItemWriterBuilder<Ticket>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("ticket_output.json"))
                .name("ticketJsonItemWriter")
                .build();
    }

    @Bean("ticketClassifierMultiFileItemWriter")
    public ClassifierCompositeItemWriter<Ticket> ticketClassifierMultiFileItemWriter() {
        ClassifierCompositeItemWriter<Ticket> writer = new ClassifierCompositeItemWriter<>();
        writer.setClassifier((Classifier<Ticket, ItemWriter<? super Ticket>>) ticket -> {
            // 始发站是上海的, 输出到文本中, 否则输出到 XML 文件中
            return "上海".equals(ticket.getDepartureStation()) ? ticketFileItemWriter() : ticketXmlItemWriter();
        });
        return writer;
    }
}
