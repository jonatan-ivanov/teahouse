package org.example.teahouse.core.actuator.health;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Only single-level inheritance supported by Feign and {@link HealthIndicator} extends {@link org.springframework.boot.actuate.health.HealthContributor}
 * so returning a Health instance won't work.
 * Also, {@link org.springframework.boot.actuate.health.Health} is not deserializable, see {@link HealthResponse}.
 */
public interface HealthClient {
    @GetMapping("/actuator/health")
    HealthResponse health();
}
