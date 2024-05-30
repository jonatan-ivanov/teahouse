package org.example.teahouse.tea;

import io.micrometer.observation.ObservationRegistry;
import org.example.teahouse.tea.service.DefaultTeaService;
import org.example.teahouse.tea.service.MakeTeaConvention;
import org.example.teahouse.tea.service.ObservedTeaService;
import org.example.teahouse.tea.service.TeaService;
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
@ComponentScan(basePackages = { "org.example.teahouse" })
public class TeaServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TeaServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }

    @Bean
    TeaService teaService(WaterClient waterClient, TealeafClient tealeafClient, ObservationRegistry registry, ObjectProvider<MakeTeaConvention> customConvention) {
        return new ObservedTeaService(new DefaultTeaService(waterClient, tealeafClient), registry, customConvention.getIfAvailable());
    }
}
