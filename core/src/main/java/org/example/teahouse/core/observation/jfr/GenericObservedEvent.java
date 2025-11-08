package org.example.teahouse.core.observation.jfr;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.observation.Observation;
import jdk.jfr.Label;

import java.util.stream.Collectors;

@Label("GenericObservedEvent")
public class GenericObservedEvent<C extends Observation.ContextView> extends ObservedEvent<C> {
    protected String keyValues;

    public GenericObservedEvent(C context) {
        super(context);
    }

    @Override
    public void enhance(C context) {
        this.keyValues = toString(context.getAllKeyValues());
    }

    private String toString(KeyValues keyValues) {
        return keyValues.stream()
            .map(this::toString)
            .collect(Collectors.joining(",", "[", "]"));
    }

    private String toString(KeyValue keyValue) {
        return "%s=\"%s\"".formatted(keyValue.getKey(), keyValue.getValue());
    }
}
