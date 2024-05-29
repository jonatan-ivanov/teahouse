package org.example.teahouse.proxy;

import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:build.properties")
@ComponentScan(basePackages = {"org.example.teahouse"})
public class ProxyServiceApplication {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication springApplication = new SpringApplication(ProxyServiceApplication.class);
        springApplication.setApplicationStartup(new BufferingApplicationStartup(10_000));
        springApplication.run(args);
    }

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("water-service", spec -> spec.path("/water-service/**")
                .filters(f -> f.requestRateLimiter().and().stripPrefix(1))
                .uri("lb://water-service"))
            .build();
    }

    @Bean
    KeyResolver eachRequestIsUniqueKeyResolver() {
        return _ -> Mono.just(String.valueOf(System.currentTimeMillis()));
    }
}
