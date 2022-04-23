package com.example.springbatchdemo.component.processor;

import com.example.springbatchdemo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/4/23 18:14
 */
@Configuration
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final Person person) throws Exception {

        final String firstName = Optional.ofNullable(person.getFirstName()).orElse(null);
        final String lastName = Optional.ofNullable(person.getLastName()).orElse(null);

        final Person transformedPerson = new Person();
        transformedPerson.setFirstName(firstName);
        transformedPerson.setLastName(lastName);

        log.info("Converting ({}) into ({})", person, transformedPerson);

        return transformedPerson;
    }
}
