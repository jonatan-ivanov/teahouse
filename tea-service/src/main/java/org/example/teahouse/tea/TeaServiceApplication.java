package org.example.teahouse.tea;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import org.example.teahouse.tea.service.*;
import org.example.teahouse.tea.tealeaf.TealeafClient;
import org.example.teahouse.tea.water.WaterClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableFeignClients
@SpringBootApplication
@PropertySource("classpath:build.properties")
@ComponentScan(basePackages = {"org.example.teahouse"})
public class TeaServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TeaServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }

    @Bean
    TeaService teaService(ObservedTeaService observedTeaService) {
        return new JfrMonitoredTeaService(observedTeaService);
    }


    @Bean
    ObservedTeaService observedTeaService(JmxMonitoredTeaService jmxMonitoredTeaService, MeterRegistry meterRegistry, Tracer tracer, ObservationRegistry observationRegistry) {
        return new ObservedTeaService(jmxMonitoredTeaService, meterRegistry, tracer, observationRegistry);
    }

    @Bean
    JmxMonitoredTeaService jmxMonitoredTeaService(WaterClient waterClient, TealeafClient tealeafClient) {
        return new JmxMonitoredTeaService(new DefaultTeaService(waterClient, tealeafClient));
    }
}
