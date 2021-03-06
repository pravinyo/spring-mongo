package com.keysoft.mongodb.configuration;

import com.keysoft.mongodb.model.ZonedDateTimeReadConverter;
import com.keysoft.mongodb.model.ZonedDateTimeWriteConverter;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    public MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

    protected String getDatabaseName() {
        return "trackzilla";
    }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
