package com.example.springbatchdemo.component.processor;

import com.example.springbatchdemo.entity.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author zourongsheng
 * @version 1.0
 * @date 2022/6/12 18:30
 */
@Component
public class TicketItemProcessor implements ItemProcessor<Ticket, Ticket> {

    private static final Logger log = LoggerFactory.getLogger(TicketItemProcessor.class);

    @Override
    public Ticket process(final Ticket ticketSource) throws Exception {

        final String departureStation = ticketSource.getDepartureStation();
        final String arrivalStation = ticketSource.getArrivalStation();
        final BigDecimal price = ticketSource.getPrice();

        final Ticket ticketTarget = new Ticket();
        ticketTarget.setDepartureStation(departureStation);
        ticketTarget.setArrivalStation(arrivalStation);
        ticketTarget.setPrice(price);

        return ticketTarget;
    }
}
