package org.example.teahouse.water.observation;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.ObservationFilter;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationPredicate;
import org.example.teahouse.water.observation._03_context.WaterFetcherObservationHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class WaterObservabilityConfiguration {

//    @Bean
    ObservationHandler<WaterFetcherContext> myLoggingObservationHandler() {
        return new WaterFetcherObservationHandler();
    }

//    @Bean
    ObservationFilter myObservationFilter() {
        return context -> {
            if (context instanceof WaterFetcherContext) {
                context.setName("renamed_water_context");
                context.addLowCardinalityKeyValue(KeyValue.of("environment", "dev"));
            }
            return context;
        };
    }

    @Bean
    ObservationPredicate myObservationPredicate() {
        return (name, context) -> {
            if (name.equals("I should be ignored")) {
                return false;
            }
            return !(context instanceof SomeContextToIgnore);
        };
    }
}
