package org.example.teahouse.water.observation._04_convention;

import io.micrometer.common.KeyValues;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationConvention;
import org.example.teahouse.water.observation.WaterFetcherContext;

public class DefaultWaterObservationConvention implements WaterObservationConvention {
    @Override
    public KeyValues getLowCardinalityKeyValues(WaterFetcherContext context) {
        return KeyValues.of(WaterObservationDocumentation.LowCardinalityKeys.ENVIRONMENT.withValue("development"));
    }

    @Override
    public KeyValues getHighCardinalityKeyValues(WaterFetcherContext context) {
        return KeyValues.of(WaterObservationDocumentation.HighCardinalityKeys.WATER_SIZE.withValue(context.getSize()));
    }

    @Override
    public String getName() {
        return "water3.find-by-size";
    }

    @Override
    public String getContextualName(WaterFetcherContext context) {
        return "find-by-size [" + context.getSize() + "]";
    }
}
