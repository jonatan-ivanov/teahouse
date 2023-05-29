package org.example.teahouse.water.observation._01_doubleinstrumentation;

import java.util.Optional;
import java.util.function.Function;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.water.controller.WaterFetcher;
import org.example.teahouse.water.repo.Water;

@RequiredArgsConstructor
public class WaterServiceMetrics implements WaterFetcher {

    private final MeterRegistry meterRegistry;

    private final Function<String, Optional<Water>> function;

    @Override
    public Optional<Water> findBySize(String size) {
        Timer timer = meterRegistry.timer("water.by.size");
        return timer.record(() -> function.apply(size));
    }
}
