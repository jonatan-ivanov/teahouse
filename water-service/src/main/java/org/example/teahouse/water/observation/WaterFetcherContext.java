package org.example.teahouse.water.observation;

import io.micrometer.observation.Observation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class WaterFetcherContext extends Observation.Context {

    @Getter
    private final String size;

    @Getter
    private final Class<?> waterFetcher;

}
