package org.example.teahouse.reporting;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BatchInsertData {

    private static final Logger log = LoggerFactory.getLogger(BatchInsertData.class);

    public static void main() {
//         System.setProperty("server.port", "9876");
        System.setProperty("spring.cloud.function.definition", "foo");
        SpringApplication springApplication = new SpringApplication(ReportingServiceApplication.class, BatchInsertData.class);
        springApplication.run();
    }

    @Bean
    CommandLineRunner commandLineRunner(MongoTemplate mongoTemplate) {
        return args -> {
            log.info("Batch inserting orders to Mongodb");
            // for loop 1000 x 1000
            int counter = 1;
            int iMax = 1000;
            int jMax = 1000;
            for (int i = 0; i < iMax; i++) {
                List<Order> orders = new ArrayList<>();
                for (int j = 0; j < jMax; j++) {
                    orders.add(new Order(System.currentTimeMillis() - j, generateTeaName(), generateTeaSize()));
                }
                mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Order.class)
                    .insert(orders)
                    .execute();
                log.info("Inserted {} / {} entries.", counter * jMax, iMax * jMax);
                counter++;
            }
            System.exit(0);
        };
    }

    String generateTeaName() {
        double random = Math.random();
        if (random < 0.1) return "english breakfast";
        else if (random < 0.7) return "sencha";
        else if (random < 0.9) return "da hong pao";
        else return "gyokuro";
    }

    String generateTeaSize() {
        double random = Math.random();
        if (random < 0.5) return "small";
        else if (random < 0.75) return "medium";
        else return "large";
    }
}
