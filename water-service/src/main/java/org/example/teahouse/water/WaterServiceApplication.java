package org.example.teahouse.water;

import org.example.teahouse.core.actuator.config.ActuatorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ActuatorConfig.class)
public class WaterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WaterServiceApplication.class, args);
    }
}
