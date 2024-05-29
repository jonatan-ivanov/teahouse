package org.example.teahouse.reporting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

// TODO: Add missing index
//@Configuration
public class MongoConfig {
    @Bean
    public void ensureIndexes(MongoTemplate mongoTemplate) {
        mongoTemplate.indexOps(Order.class).ensureIndex(new Index().on("customerId", Sort.Direction.ASC));
    }
}
