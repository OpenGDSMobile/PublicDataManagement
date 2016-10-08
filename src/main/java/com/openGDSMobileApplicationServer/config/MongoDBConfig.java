package com.openGDSMobileApplicationServer.config;

import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * Created by intruder on 16. 8. 13.
 */
@Configuration
public class MongoDBConfig {

    Logger log = LoggerFactory.getLogger(MongoDBConfig.class);
    @Bean
    MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = null;
        try {
            //mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "opengdsmobiledata");
            mongoTemplate = new MongoTemplate(new MongoClient(System.getProperty("myapplication.ip")), "opengdsmobiledata");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mongoTemplate;
    }

}
