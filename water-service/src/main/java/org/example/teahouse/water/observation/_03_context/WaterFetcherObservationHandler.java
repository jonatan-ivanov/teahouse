package org.example.teahouse.water.observation._03_context;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.observation.WaterFetcherContext;

@Slf4j
public class WaterFetcherObservationHandler implements ObservationHandler<WaterFetcherContext> {

    @Override
    public void onStart(WaterFetcherContext context) {
        log.info("<Handler> On start from class <" + context.getWaterFetcher() + ">");
    }

    @Override
    public void onError(WaterFetcherContext context) {
        log.info("<Handler> On error");
    }

    @Override
    public void onEvent(Observation.Event event, WaterFetcherContext context) {
        log.info("<Handler> On event");
    }

    @Override
    public void onScopeOpened(WaterFetcherContext context) {
        log.info("<Handler> On scope opened");
    }

    @Override
    public void onScopeClosed(WaterFetcherContext context) {
        log.info("<Handler> On scope closed");
    }

    @Override
    public void onScopeReset(WaterFetcherContext context) {
        log.info("<Handler> On scope reset");
    }

    @Override
    public void onStop(WaterFetcherContext context) {
        log.info("<Handler> On stop");
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return context instanceof WaterFetcherContext;
    }
}
