package com.ruoyi.manage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing// 启用MongoDB审计功能
public class MongoDBConfig {
    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory dbFactory) {
        return new MongoTemplate(dbFactory);
    }
}
