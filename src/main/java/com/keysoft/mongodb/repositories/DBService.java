package com.keysoft.mongodb.repositories;

import com.keysoft.mongodb.model.Application;
import com.keysoft.mongodb.model.Release;
import com.keysoft.mongodb.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public ApplicationRepository getApplicationRepository() {
        return applicationRepository;
    }

    public ReleaseRepository getReleaseRepository() {
        return releaseRepository;
    }

    public TicketRepository getTicketRepository() {
        return ticketRepository;
    }

    public void updateApplicationWithCriteria(Application application){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(application.getName()));

        Update update = new Update();
        update.set("name","Post master");
        mongoTemplate.updateFirst(query, update, Application.class);
    }


    public List<Release> getReleaseByTicketStatus(String status) {
        Query query = new Query();

        //embedded document use tickets.status
        query.addCriteria(Criteria.where("tickets.status").is(status));

        //repository is not flexible to do this, using mongoTemplate
        return mongoTemplate.find(query, Release.class);
    }


    public Double getReleaseCost(String id) {
        Double defaultCost = 0.0;
        Release release = mongoTemplate.findById(id,Release.class);
        if (release!=null)  return release.getEstimatedCosts();
        return defaultCost;
    }

    public Release insertReleaseWithTicket(Release release) {
        mongoTemplate.insert(release);
        return release;
    }

    public List<Ticket>  getTicketsWithReleaseDateGt(ZonedDateTime zonedDateTime, int page){
        logger.debug("Time: {}",zonedDateTime);
        logger.debug("Page: {}",page);

        Query query = new Query();
        query.addCriteria(Criteria.where("releaseDate").gt(zonedDateTime));
        query.with(Sort.by(Sort.Direction.DESC, "releaseDate"));
        query.with(PageRequest.of(page, 3));
        query.fields().include("tickets");

        List<Release> releaseList = mongoTemplate.find(query, Release.class);
        List<Ticket> ticketList = new ArrayList<>();
        releaseList.forEach(release -> ticketList.addAll(release.getTickets()));
        return ticketList;
    }
}
