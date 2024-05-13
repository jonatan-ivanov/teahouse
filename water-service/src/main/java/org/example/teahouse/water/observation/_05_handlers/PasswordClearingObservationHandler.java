package org.example.teahouse.water.observation._05_handlers;

import java.util.List;
import java.util.stream.Collectors;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.teahouse.water.observation.WaterFetcherContext;

import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PasswordClearingObservationHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        removePotentialPasswords(context);
    }

    @Override
    public void onStop(Observation.Context context) {
        removePotentialPasswords(context);
    }

    private static void removePotentialPasswords(Observation.Context context) {
        List<KeyValue> potentialPasswords = context.getHighCardinalityKeyValues().stream().filter(keyValue -> keyValue.getKey().toLowerCase().contains("password")).toList();
        if (!potentialPasswords.isEmpty()) {
            log.warn("Found {} potential passwords in key values. Will remove them", potentialPasswords.size());
        }
        potentialPasswords.forEach(keyValue -> context.removeHighCardinalityKeyValue(keyValue.getKey()));
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}
