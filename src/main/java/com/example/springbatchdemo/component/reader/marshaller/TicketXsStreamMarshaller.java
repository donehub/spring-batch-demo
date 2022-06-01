package com.example.springbatchdemo.component.reader.marshaller;

import com.example.springbatchdemo.entity.Ticket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/1 22:15
 */
@Configuration
public class TicketXsStreamMarshaller {

    @Bean("ticketMarshaller")
    public XStreamMarshaller ticketMarshaller() {

        Map<String, Class<Ticket>> aliases = new HashMap<>(1);
        aliases.put("ticket", Ticket.class);

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);

        return marshaller;
    }
}
