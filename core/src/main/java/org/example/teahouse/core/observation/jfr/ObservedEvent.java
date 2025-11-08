package org.example.teahouse.core.observation.jfr;

import io.micrometer.observation.Observation;
import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

@Category("Observation")
@Label("ObservedEvent")
@Description("JFR Event created from an Observation")
public abstract class ObservedEvent<C extends Observation.ContextView> extends Event {
    protected String name;
    protected final Class<? extends Observation.ContextView> contextType;
    protected String contextualName;
    protected String error;

    public ObservedEvent(C context) {
        this.name = context.getName();
        this.contextType = context.getClass();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContextualName(String contextualName) {
        this.contextualName = contextualName;
    }

    public void setError(Throwable error) {
        this.error = String.valueOf(error);
    }

    public abstract void enhance(C  context);
}
