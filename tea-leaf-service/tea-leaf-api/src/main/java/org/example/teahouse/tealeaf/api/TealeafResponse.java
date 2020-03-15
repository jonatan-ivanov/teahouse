package org.example.teahouse.tealeaf.api;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class TealeafResponse {
    private final Long id;
    private final String name;
    private final String type;
    private final String suggestedAmount;
    private final String suggestedWaterTemperature;
    private final String suggestedSteepingTime;
}
