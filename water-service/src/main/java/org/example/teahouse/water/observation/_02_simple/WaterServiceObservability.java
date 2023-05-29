package org.example.teahouse.water.observation._02_simple;

import java.util.Optional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.repo.Water;
import org.example.teahouse.water.controller.WaterFetcher;
import org.example.teahouse.water.repo.WaterRepository;

//@Service
@Slf4j
@RequiredArgsConstructor
public class WaterServiceObservability implements WaterFetcher {

    private final ObservationRegistry observationRegistry;

    private final WaterRepository waterRepository;

    // Aspect
    @Observed(name = "water2.by.size", contextualName = "find-by-size", lowCardinalityKeyValues = {
        "foo", "bar"
    })
    @Override
    public Optional<Water> findBySize(String size) {
        // API
        Observation observation = Observation.createNotStarted("water2.child-by-size", observationRegistry)
            .contextualName("water-by-size")
            .lowCardinalityKeyValue("foo2", "bar2")
            .highCardinalityKeyValue("size", size);
        return observation.observe(() -> {
            log.info("This will have a trace id");
            Optional<Water> bySize = waterRepository.findBySize(size);
            observation.event(() -> "water-by-size.calculated");
            return bySize;
        });
    }
}
