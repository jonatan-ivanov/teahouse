package org.example.teahouse.water.observation._04_convention;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;
import org.example.teahouse.water.observation.WaterFetcherContext;

public interface WaterObservationConvention extends ObservationConvention<WaterFetcherContext> {
    @Override
    default boolean supportsContext(Observation.Context context) {
        return context instanceof WaterFetcherContext;
    }
}
