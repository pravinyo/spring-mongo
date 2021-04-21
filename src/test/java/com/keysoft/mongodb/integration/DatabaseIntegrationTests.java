package com.keysoft.mongodb.integration;

import com.keysoft.mongodb.model.Release;
import com.keysoft.mongodb.model.Ticket;
import com.keysoft.mongodb.repositories.ReleaseRepository;
import com.keysoft.mongodb.repositories.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(DatabaseTestConfiguration.class)
public class DatabaseIntegrationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ReleaseRepository releaseRepository;

    @BeforeEach
    public void setup(){
        Ticket ticket1 = new Ticket("test1","test purpose","1","In Process");
        Ticket ticket2 = new Ticket("test2","test purpose","1","In Process");
        Ticket ticket3 = new Ticket("test3","test purpose","1","Completed");
        Ticket ticket4 = new Ticket("test4","test purpose","1","Completed");

        ticketRepository.insert(Arrays.asList(ticket1, ticket2, ticket3, ticket4));

        List<Ticket> ticketList1 = Arrays.asList(ticket1, ticket2);
        List<Ticket> ticketList2 = Arrays.asList(ticket3, ticket4);
        Release release1 = new Release("Release1","for testing",ticketList1, ZonedDateTime.now());
        Release release2 = new Release("Release2","for testing",ticketList2, ZonedDateTime.now());

        releaseRepository.insert(Arrays.asList(release1, release2));
    }

    @Test
    public void findAllCustomerQueryAndStream(){
        Stream<Ticket> stream = ticketRepository.findAllCustomerQueryAndStream("Completed");

        List<Ticket> ticketList = stream.collect(Collectors.toList());

        Assertions.assertNotNull(ticketList);
        Assertions.assertEquals(2, ticketList.size());
        Assertions.assertEquals("test3", ticketList.get(0).getTitle());
    }

    @Test
    public void findByMinTicketsInProcess(){
        List<Release> releaseList = releaseRepository.findByMinTicketsInProcess(1);
        Assertions.assertEquals(1, releaseList.size());
    }

    @AfterEach
    public void tearDown(){
        mongoTemplate.dropCollection(Release.class);
        mongoTemplate.dropCollection(Ticket.class);
    }

}
