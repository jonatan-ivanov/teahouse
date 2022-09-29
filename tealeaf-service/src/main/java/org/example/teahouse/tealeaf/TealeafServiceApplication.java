package org.example.teahouse.tealeaf;

import org.example.teahouse.core.actuator.config.ActuatorConfig;
import org.example.teahouse.core.log.access.AccessLogConfig;
import org.example.teahouse.core.observability.ObservabilityConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@EnableDiscoveryClient
@SpringBootApplication
@PropertySource("classpath:build.properties")
@Import({ActuatorConfig.class, AccessLogConfig.class, ObservabilityConfig.class})
public class TealeafServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TealeafServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }
}
