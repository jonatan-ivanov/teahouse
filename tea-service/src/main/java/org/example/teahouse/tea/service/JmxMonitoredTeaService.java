package org.example.teahouse.tea.service;

import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;
import org.springframework.jmx.export.annotation.*;

import java.util.Collection;
import java.util.List;
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
    @ManagedOperation(description = "Makes a tea")
    @ManagedOperationParameters({
        @ManagedOperationParameter(name = "name", description = "Name of the tea"),
        @ManagedOperationParameter(name = "size", description = "Size of the tea")
    })
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

    @ManagedAttribute(description = "Available tea leaves")
    public List<String> getTeaLeavesFormatted() {
        return tealeaves().stream()
            .map(SimpleTealeafModel::getName)
            .sorted()
            .toList();
    }

    @ManagedAttribute(description = "Available waters")
    public List<String> getWatersFormatted() {
        return waters().stream()
            .map(water -> water.getSize() + " (" + water.getAmount() + ")")
            .sorted()
            .toList();
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
        final var water = response.getWater();
        final var tealeaf = response.getTealeaf();
        return (water != null ? water.getAmount() : "?") + " " +
               (tealeaf != null ? tealeaf.getName() : "?") +
               ", steeping time: " + response.getSteepingTime() +
               (water != null && water.getTemperature() != null ? ", water temp: " + water.getTemperature() : "");
    }

}
