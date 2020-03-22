package org.example.teahouse.tea;

import org.example.teahouse.core.actuator.config.ActuatorConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@SpringBootApplication
@Import(ActuatorConfig.class)
public class TeaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeaServiceApplication.class, args);
    }
}
