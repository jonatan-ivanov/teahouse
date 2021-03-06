package org.example.teahouse.water;

import org.example.teahouse.core.actuator.config.ActuatorConfig;
import org.example.teahouse.core.log.access.AccessLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;

@EnableDiscoveryClient
@SpringBootApplication
@PropertySource("classpath:build.properties")
@Import({ActuatorConfig.class, AccessLogConfig.class, SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class WaterServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WaterServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }
}
