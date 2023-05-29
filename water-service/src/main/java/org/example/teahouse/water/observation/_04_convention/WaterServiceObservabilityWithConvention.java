package org.example.teahouse.water.observation._04_convention;

import java.util.Optional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.controller.WaterFetcher;
import org.example.teahouse.water.observation.WaterFetcherContext;
import org.example.teahouse.water.repo.Water;
import org.example.teahouse.water.repo.WaterRepository;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WaterServiceObservabilityWithConvention implements WaterFetcher {

    private final ObservationRegistry observationRegistry;

    private final WaterRepository waterRepository;

    private WaterObservationConvention customWaterObservationConvention;

    @Override
    public Optional<Water> findBySize(String size) {
        WaterFetcherContext waterFetcherContext = new WaterFetcherContext(size, WaterServiceObservabilityWithConvention.class);
        Observation observation = WaterObservationDocumentation.FIND_BY_SIZE.observation(customWaterObservationConvention, new DefaultWaterObservationConvention(), () -> waterFetcherContext, observationRegistry);
        return observation.observe(() -> {
            log.info("This will have a trace id");
            Optional<Water> bySize = waterRepository.findBySize(size);
            observation.event(WaterObservationDocumentation.Events.WATER_CALCULATED);
            return bySize;
        });
    }

    public void setCustomWaterObservationConvention(WaterObservationConvention customWaterObservationConvention) {
        this.customWaterObservationConvention = customWaterObservationConvention;
    }
}
