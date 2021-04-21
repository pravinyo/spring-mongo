package com.keysoft.mongodb.integration;

import com.keysoft.mongodb.configuration.AppConfig;
import com.keysoft.mongodb.configuration.GenericCascadeListener;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@TestConfiguration
public class DatabaseTestConfiguration {
    @Bean
    GenericCascadeListener genericCascadeListener(){
        return new GenericCascadeListener();
    }

    @Bean
    MongoCustomConversions getCustomConversion(){
        AppConfig appConfig = new AppConfig();
        return appConfig.customConversions();
    }
}
