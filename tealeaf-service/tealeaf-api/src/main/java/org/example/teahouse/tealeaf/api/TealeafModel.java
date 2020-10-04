package org.example.teahouse.tealeaf.api;

import java.util.UUID;

public interface TealeafModel {
    UUID getId();
    String getName();
    String getType();
    String getSuggestedAmount();
    String getSuggestedWaterTemperature();
    String getSuggestedSteepingTime();
}
