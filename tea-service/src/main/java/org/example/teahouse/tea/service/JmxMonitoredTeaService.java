package org.example.teahouse.tea.service;

import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

@ManagedResource(objectName = "org.example.teahouse.tea:type=TeaService,name=teaService", description = "Tea Service")
public class JmxMonitoredTeaService implements TeaService {
    private final TeaService delegate;
    private final LongAdder cupsMade = new LongAdder();

    private final AtomicReference<TeaResponse> lastResponse = new AtomicReference<>();

    public JmxMonitoredTeaService(TeaService delegate) {
        this.delegate = delegate;
    }

    @Override
    public TeaResponse make(String name, String size) {
        final var make = delegate.make(name, size);
        cupsMade.increment();
        lastResponse.set(make);
        return make;
    }

    @Override
    public Collection<SimpleTealeafModel> tealeaves() {
        return delegate.tealeaves();
    }

    @Override
    public Collection<SimpleWaterModel> waters() {
        return delegate.waters();
    }

    @ManagedAttribute(description = "Number of cups made")
    public long getCupsMade() {
        return cupsMade.longValue();
    }

    @ManagedAttribute(description = "Last made cup of tea")
    public String getLastCupOfTea() {
        final var response = lastResponse.get();
        if (response == null) {
            return "No tea made yet";
        }
        final var water = response.water();
        final var tealeaf = response.tealeaf();
        return (water != null ? water.amount() : "?") + " " +
               (tealeaf != null ? tealeaf.name() : "?") +
               ", steeping time: " + response.steepingTime() +
               (water != null && water.temperature() != null ? ", water temp: " + water.temperature() : "");
    }

}
