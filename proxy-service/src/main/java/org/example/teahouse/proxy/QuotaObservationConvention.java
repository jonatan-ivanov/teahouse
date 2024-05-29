package org.example.teahouse.proxy;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;

public interface QuotaObservationConvention extends ObservationConvention<QuotaObservationContext> {

    @Override
    default boolean supportsContext(Observation.Context context) {
        return context instanceof QuotaObservationContext;
    }
}
