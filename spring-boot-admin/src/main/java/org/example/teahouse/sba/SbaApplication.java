package org.example.teahouse.sba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.example.teahouse.core.actuator.config.CommonActuatorConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@EnableAdminServer
@SpringBootApplication
@PropertySource("classpath:build.properties")
@Import({CommonActuatorConfig.class})
public class SbaApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SbaApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }
}
