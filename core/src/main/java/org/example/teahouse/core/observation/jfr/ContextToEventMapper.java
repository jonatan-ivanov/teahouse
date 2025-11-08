package org.example.teahouse.core.observation.jfr;

import io.micrometer.observation.Observation;

public interface ContextToEventMapper<C extends Observation.ContextView> {
    ObservedEvent<C> map(C context);

    Class<C> supportedContextType();
}
