package com.openGDSMobileApplicationServer.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by intruder on 16. 8. 13.
 */
@Configuration
public class MongoDBConfig {

    @Bean
    MongoTemplate mongoTemplate() throws Exception{
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "opengdsmobiledata");

        return mongoTemplate;
    }

}
