package org.example.teahouse.core.actuator.health;

import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

public class HealthResponse extends HashMap<String, Object> {
    public Health toHealth() {
        Map<String, Object> details = this.entrySet().stream()
            .filter(entry -> !"status".equals(entry.getKey()))
            .collect(toUnmodifiableMap(Entry::getKey, Entry::getValue));

        return Health
            .status(new Status(String.valueOf(this.get("status"))))
            .withDetails(details)
            .build();
    }
}
