package org.example.teahouse.eureka;

import org.example.teahouse.core.actuator.config.CommonActuatorConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@EnableEurekaServer
@SpringBootApplication
@PropertySource("classpath:build.properties")
@Import({CommonActuatorConfig.class})
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EurekaApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }
}
