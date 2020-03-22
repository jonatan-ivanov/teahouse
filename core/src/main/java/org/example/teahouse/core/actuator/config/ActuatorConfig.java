package org.example.teahouse.core.actuator.config;

import org.example.teahouse.core.actuator.info.RuntimeInfoContributor;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ActuatorConfig {
    @Bean
    public InfoContributor runtimeInfoContributor(Environment environment) {
        return new RuntimeInfoContributor(environment);
    }
}
