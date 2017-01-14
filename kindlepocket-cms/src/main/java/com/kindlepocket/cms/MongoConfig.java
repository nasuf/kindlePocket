package com.kindlepocket.cms;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
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
        String database = new MongoClientURI("mongodb://120.25.224.11/kindlepocket").getDatabase();
        Mongo mongo = null;
        UserCredentials credential = getUserCredentials();
        try {
			mongo = new Mongo("120.25.224.11", 28888);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        
        //return new SimpleMongoDbFactory(client(), database);
        return new SimpleMongoDbFactory(mongo, database, credential);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
    
    @Bean
    public UserCredentials getUserCredentials() {
    	return new UserCredentials("nasuf","blxyST103166");
    }
    
}
