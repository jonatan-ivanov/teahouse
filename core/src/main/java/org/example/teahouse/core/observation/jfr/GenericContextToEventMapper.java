package org.example.teahouse.core.observation.jfr;

import io.micrometer.observation.Observation;

public class GenericContextToEventMapper implements ContextToEventMapper<Observation.Context> {
    @Override
    public ObservedEvent<Observation.Context> map(Observation.Context context) {
        return new GenericObservedEvent<>(context);
    }

    @Override
    public Class<Observation.Context> supportedContextType() {
        return Observation.Context.class;
    }
}
