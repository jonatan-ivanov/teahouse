package org.example.teahouse.reporting;

import jakarta.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

// TODO: Add missing index
//@Configuration
public class MongoConfig {

    private final MongoTemplate mongoTemplate;

    public MongoConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void ensureIndexes() {
        mongoTemplate.indexOps(Order.class).ensureIndex(new Index().on("tealeaf", Sort.Direction.ASC));
    }
}
