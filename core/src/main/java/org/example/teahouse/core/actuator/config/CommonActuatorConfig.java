package org.example.teahouse.core.actuator.config;

import feign.Request;
import feign.micrometer.FeignContext;
import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.example.teahouse.core.actuator.info.RuntimeInfoContributor;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.observation.ServerRequestObservationContext;

@Configuration
public class CommonActuatorConfig {
    @Bean
    public InfoContributor runtimeInfoContributor(Environment environment) {
        return new RuntimeInfoContributor(environment);
    }

    @Bean
    public ObservationPredicate actuatorPredicate() {
        return (name, context) -> {
            if (name.equals("http.server.requests")) {
                HttpServletRequest request = ((ServerRequestObservationContext) context).getCarrier();
                return !request.getRequestURI().startsWith("/actuator");
            }
            else if (name.equals("http.client.requests")){
                Request request = ((FeignContext) context).getCarrier();
                return !request.url().endsWith("/actuator/health");
            }
            else {
                return true;
            }
        };
    }

    @Bean
    public ObservationRegistryCustomizer<ObservationRegistry> tempoCustomizer() {
        return registry -> registry.observationConfig()
            .observationFilter(this::orgFilter)
            .observationFilter(this::tempoFilter);
    }

    private Observation.Context orgFilter(Observation.Context context) {
        return context.addLowCardinalityKeyValue(KeyValue.of("org", "teahouse"));
    }

    // TODO: remove this once Tempo is fixed: https://github.com/grafana/tempo/issues/1916
    private Observation.Context tempoFilter(Observation.Context context) {
        if (context.getError() != null) {
            context.addHighCardinalityKeyValue(KeyValue.of("error", "true"));
            context.addHighCardinalityKeyValue(KeyValue.of("errorMessage", context.getError().getMessage()));
        }

        return context;
    }
}
