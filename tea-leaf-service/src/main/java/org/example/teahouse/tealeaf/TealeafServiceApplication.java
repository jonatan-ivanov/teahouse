package org.example.teahouse.tealeaf;

import org.example.teahouse.core.actuator.config.ActuatorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ActuatorConfig.class)
public class TealeafServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TealeafServiceApplication.class, args);
    }
}
