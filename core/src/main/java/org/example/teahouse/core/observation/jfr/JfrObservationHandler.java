package org.example.teahouse.core.observation.jfr;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JfrObservationHandler implements ObservationHandler<Observation.Context> {

    private final Map<Class<?>, ContextToEventMapper<?>> mappers;
    private final ContextToEventMapper<?> defaultMapper;

    public JfrObservationHandler() {
        this(new GenericContextToEventMapper());
    }

    public JfrObservationHandler(ContextToEventMapper<?> defaultMapper) {
        this.mappers = new HashMap<>();
        this.defaultMapper = defaultMapper;
    }

    public void register(ContextToEventMapper<?> mapper) {
        mappers.put(mapper.supportedContextType(), mapper);
    }

    @Override
    public void onStart(Observation.@NonNull Context context) {
        ContextToEventMapper mapper = findMapper(context);
        ObservedEvent<?> event = mapper.map(context);
        event.begin();
        context.put(ObservedEvent.class, event);
    }

    private ContextToEventMapper<?> findMapper(Observation.@NonNull Context context) {
        ContextToEventMapper<?> mapper = mappers.get(context.getClass());
        return mapper != null ? mapper : defaultMapper;
    }

    @Override
    public void onError(Observation.@NonNull Context context) {
        ObservedEvent<?> event = context.getRequired(ObservedEvent.class);
        event.setError(context.getError());
    }

    @Override
    public void onStop(Observation.@NonNull Context context) {
        ObservedEvent<Observation.ContextView> event = context.getRequired(ObservedEvent.class);
        event.setName(context.getName());
        event.setContextualName(context.getContextualName());
        event.enhance(context);
        event.commit();
    }

    @Override
    public boolean supportsContext(Observation.@NonNull Context context) {
        return true;
    }

}
