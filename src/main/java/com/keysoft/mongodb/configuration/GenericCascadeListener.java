package com.keysoft.mongodb.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class GenericCascadeListener extends AbstractMongoEventListener<Object> {

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        logger.info("Action before object save");
        super.onBeforeSave(event);
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Object> event) {
        logger.info("Action After object save");
        super.onAfterSave(event);
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        logger.info("before converter called");
        Object document = event.getSource();

        ReflectionUtils.doWithFields(document.getClass(), docField -> {
            ReflectionUtils.makeAccessible(docField);

            if (docField.isAnnotationPresent(DBRef.class)){
                final Object fieldValue = docField.get(document);

                // save child
                this.mongoTemplate.save(fieldValue);
            }
        });

    }
}
