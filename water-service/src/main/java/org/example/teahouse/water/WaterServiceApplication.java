package org.example.teahouse.water;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:build.properties")
@ComponentScan(basePackages = { "org.example.teahouse" })
public class WaterServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WaterServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }
}
