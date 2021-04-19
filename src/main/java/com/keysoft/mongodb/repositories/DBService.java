package com.keysoft.mongodb.repositories;

import com.keysoft.mongodb.model.Application;
import com.keysoft.mongodb.model.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    @Autowired
    private MongoTemplate mongoTemplate;

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


}
