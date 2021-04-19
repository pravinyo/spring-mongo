package com.keysoft.mongodb.repositories;

import com.keysoft.mongodb.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServices {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateApplicationWithCriteria(Application application){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(application.getName()));

        Update update = new Update();
        update.set("name","Post master");
        mongoTemplate.updateFirst(query, update, Application.class);
    }
}
