package org.example.teahouse.tea.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jdk.jfr.Event;
import jdk.jfr.consumer.RecordingStream;
import org.example.teahouse.core.jfrsupport.JfrMeterBinder;
import org.example.teahouse.tea.api.TeaResponse;
import org.example.teahouse.tealeaf.api.SimpleTealeafModel;
import org.example.teahouse.water.api.SimpleWaterModel;

import java.util.Collection;

public class JfrMonitoredTeaService extends JfrMeterBinder implements TeaService {
    static class JfrMadeTeaEvent extends Event {
    }

    private final TeaService delegate;

    public JfrMonitoredTeaService(TeaService delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void register(MeterRegistry registry, RecordingStream recordingStream) {
        var timer = Timer.builder("tea.total.duration")
            .description("Time taken to make a tea")
            .register(registry);
        var counter = Counter.builder("tea.total.count")
            .description("Number of times a tea was made")
            .register(registry);
        recordingStream.onEvent(JfrMadeTeaEvent.class.getName(), event -> {
            timer.record(event.getDuration());
            counter.increment();
        });
    }

    @Override
    public TeaResponse make(String name, String size) {
        JfrMadeTeaEvent event = new JfrMadeTeaEvent();
        event.begin();
        TeaResponse madeTea = delegate.make(name, size);
        event.commit();
        return madeTea;
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
