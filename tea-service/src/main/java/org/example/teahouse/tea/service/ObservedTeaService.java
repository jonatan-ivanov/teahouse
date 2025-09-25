package org.example.teahouse.tea.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

public class ObservedTeaService implements TeaService {
    private final TeaService delegate;
    private final MeterRegistry meterRegistry;
    private final Tracer tracer;
    private final ObservationRegistry observationRegistry;

    private final AtomicReference<TeaResponse> lastResponse = new AtomicReference<>();

    public ObservedTeaService(TeaService delegate, MeterRegistry meterRegistry, Tracer tracer, ObservationRegistry observationRegistry) {
        this.delegate = delegate;
        this.meterRegistry = meterRegistry;
        this.tracer = tracer;
        this.observationRegistry = observationRegistry;
    }

    @Override
    public TeaResponse make(String name, String size) {
        TeaResponse response = delegate.make(name, size);
        lastResponse.set(response);
        return response;
    }

    @Override
    public Collection<SimpleTealeafModel> tealeaves() {
        return delegate.tealeaves();
    }

    @Override
    public Collection<SimpleWaterModel> waters() {
        return delegate.waters();
    }
}
