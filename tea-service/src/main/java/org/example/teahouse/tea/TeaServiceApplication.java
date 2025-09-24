package org.example.teahouse.tea;

import io.micrometer.observation.ObservationRegistry;
import org.example.teahouse.tea.service.*;
import org.example.teahouse.tea.tealeaf.TealeafClient;
import org.example.teahouse.tea.water.WaterClient;
import org.springframework.beans.factory.ObjectProvider;
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
    ObservedTeaService observedTeaService(JmxMonitoredTeaService jmxMonitoredTeaService, ObservationRegistry registry, ObjectProvider<MakeTeaConvention> customConvention) {
        return new ObservedTeaService(jmxMonitoredTeaService, registry, customConvention.getIfAvailable());
    }

    @Bean
    JmxMonitoredTeaService jmxMonitoredTeaService(WaterClient waterClient, TealeafClient tealeafClient) {
        return new JmxMonitoredTeaService(new DefaultTeaService(waterClient, tealeafClient));
    }
}
