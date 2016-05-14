package com.kindlepocket.cms;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories
public class MongoConfig {

    private static Logger logger = Logger.getLogger(MongoConfig.class);

    @Bean
    public MongoClient client() {
        MongoClient client = null;
        try {
            client = new MongoClient(new ServerAddress("120.25.224.11", 27017));
        } catch (UnknownHostException e) {
            if (logger.isErrorEnabled()) {
                logger.error("unKnownHost!", e);
            }
        }
        return client;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        String database = new MongoClientURI("mongodb://120.25.224.11/test").getDatabase();
        return new SimpleMongoDbFactory(client(), database);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

}
