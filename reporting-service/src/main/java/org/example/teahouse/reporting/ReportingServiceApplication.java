package org.example.teahouse.reporting;

import java.util.function.Consumer;

import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Hooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.observability.ContextProviderFactory;
import org.springframework.data.mongodb.observability.MongoObservationCommandListener;

@SpringBootApplication
@PropertySource("classpath:build.properties")
@ComponentScan(basePackages = {"org.example.teahouse"})
@Slf4j
public class ReportingServiceApplication {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication springApplication = new SpringApplication(ReportingServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }

    @Bean
    Consumer<Order> orderConsumer(OrderRepository orderRepository) {
        return order -> {
            log.debug("Got a new order! {}", order);
            orderRepository.save(order);
        };
    }

    // Observability for MongoDb - https://docs.spring.io/spring-data/mongodb/reference/observability/observability.html
    @Bean
    MongoClientSettingsBuilderCustomizer mongoMetricsSynchronousContextProvider(ObservationRegistry registry) {
        return (clientSettingsBuilder) -> {
            clientSettingsBuilder.contextProvider(ContextProviderFactory.create(registry))
                .addCommandListener(new MongoObservationCommandListener(registry));
        };
    }
}
