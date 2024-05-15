package org.example.teahouse.water.observation._01_doubleinstrumentation;

import java.util.Optional;
import java.util.function.Function;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.repo.Water;
import org.example.teahouse.water.controller.WaterFetcher;

@RequiredArgsConstructor
@Slf4j
public class WaterServiceTracing implements WaterFetcher {

    private final Tracer tracer;

    private final Function<String, Optional<Water>> function;

    @Override
    public Optional<Water> findBySize(String size) {
        Span span = tracer.nextSpan().name("find-by-size").tag("size", size);
        try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
            log.info("Here we will have the child span trace id injected"); // This is also an instrumentation!
            Optional<Water> bySize = function.apply(size);
            span.event("water-by-size.calculated");
            return bySize;
        } catch (Exception ex) {
            span.error(ex);
            throw new RuntimeException(ex);
        } finally {
            span.end();
        }
    }
}
