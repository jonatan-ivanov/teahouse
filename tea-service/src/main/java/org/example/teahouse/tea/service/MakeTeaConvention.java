package org.example.teahouse.tea.service;

import io.micrometer.common.lang.NonNull;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;

public interface MakeTeaConvention extends ObservationConvention<MakeTeaContext> {
    @Override
    default boolean supportsContext(@NonNull Observation.Context context) {
        return context instanceof MakeTeaContext;
    }
}
