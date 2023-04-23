package org.example.teahouse.tea.observation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.stereotype.Component;

@Component
public class SseObservationHandler implements ObservationHandler<ServerRequestObservationContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String startTimeKey = this.getClass().getName();

    private final BlockingQueue<String> queue;

    public SseObservationHandler() {
        this.queue = new LinkedBlockingQueue<>(1000);
    }

    @Override
    public void onStart(@Nonnull ServerRequestObservationContext context) {
        context.put(this.startTimeKey, System.nanoTime());
    }

    @Override
    public void onStop(@Nonnull ServerRequestObservationContext context) {
        try {
            if (this.queue.remainingCapacity() < 1) {
                this.queue.poll();
            }
            this.queue.put(contextToMessage(context));
        }
        catch (InterruptedException e) {
            this.logger.warn("Unable to add message", e);
        }
    }

    @Override
    public boolean supportsContext(@Nonnull Observation.Context context) {
        return context instanceof ServerRequestObservationContext;
    }

    @Nonnull
    public String take() throws InterruptedException {
        return this.queue.take();
    }

    @Nonnull
    private String contextToMessage(@Nonnull ServerRequestObservationContext context) {
        return "{\"name\": \"%s\", \"error\": %b, \"duration\": %d}".formatted(context.getName(), context.getError() !=null , duration(context));
    }

    private long duration(@Nonnull Observation.Context context) {
        @SuppressWarnings("DataFlowIssue")
        long nanos = System.nanoTime() - context.<Long>get(this.startTimeKey);
        return TimeUnit.NANOSECONDS.toMillis(nanos);
    }
}
