package org.example.teahouse.tealeaf.api;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SimpleTealeafModel implements TealeafModel{
    private final UUID id;
    private final String name;
    private final String type;
    private final String suggestedAmount;
    private final String suggestedWaterTemperature;
    private final String suggestedSteepingTime;
}
