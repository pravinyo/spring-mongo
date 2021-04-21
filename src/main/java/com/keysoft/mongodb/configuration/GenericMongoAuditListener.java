package com.keysoft.mongodb.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GenericMongoAuditListener extends AbstractMongoEventListener<Object> {

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void onAfterSave(AfterSaveEvent<Object> event) {
        Object obj = event.getSource();
        logger.info("{} After save Document {}", LocalDateTime.now(), obj);
    }

    @Override
    public void onAfterLoad(AfterLoadEvent<Object> event) {
        Object obj = event.getSource();
        logger.info("{} After Load Document {}", LocalDateTime.now(), obj);
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Object> event) {
        Object obj = event.getSource();
        logger.info("{} Deleted Document {}", LocalDateTime.now(), obj);
    }
}
