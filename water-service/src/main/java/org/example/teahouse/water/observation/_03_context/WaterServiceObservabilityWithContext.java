package org.example.teahouse.water.observation._03_context;

import java.util.Optional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.controller.WaterFetcher;
import org.example.teahouse.water.observation.WaterFetcherContext;
import org.example.teahouse.water.repo.Water;
import org.example.teahouse.water.repo.WaterRepository;

//@Service
@Slf4j
@RequiredArgsConstructor
public class WaterServiceObservabilityWithContext implements WaterFetcher {

    private final ObservationRegistry observationRegistry;

    private final WaterRepository waterRepository;

    @Override
    public Optional<Water> findBySize(String size) {
        WaterFetcherContext waterFetcherContext = new WaterFetcherContext(size, WaterServiceObservabilityWithContext.class);
        Observation observation = Observation.createNotStarted("water2.child-by-size", () -> waterFetcherContext, observationRegistry)
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
