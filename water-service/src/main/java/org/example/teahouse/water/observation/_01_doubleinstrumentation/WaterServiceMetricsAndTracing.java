package org.example.teahouse.water.observation._01_doubleinstrumentation;

import java.util.Optional;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.example.teahouse.water.repo.Water;
import org.example.teahouse.water.controller.WaterFetcher;
import org.example.teahouse.water.repo.WaterRepository;

/*
       !!!! YOU WILL HAVE TO DO IT FOR YOUR BUSINESS LOGIC !!!

       EVERY
                SINGLE
                            TIME
 */

@RequiredArgsConstructor
// @Service
public class WaterServiceMetricsAndTracing implements WaterFetcher {

    private final MeterRegistry meterRegistry;

    private final Tracer tracer;

    private final WaterRepository waterRepository;

    @Override
    public Optional<Water> findBySize(String size) {
        return new WaterServiceTracing(tracer, s -> new WaterServiceMetrics(meterRegistry, waterRepository::findBySize).findBySize(s)).findBySize(size);
    }
}
