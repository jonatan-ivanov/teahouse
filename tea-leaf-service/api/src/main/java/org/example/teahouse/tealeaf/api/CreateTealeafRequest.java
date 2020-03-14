package org.example.teahouse.tealeaf.api;

import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class CreateTealeafRequest {
    @Size(min = 1, max = 10)
    private final String name;

    @Size(min = 1, max = 10)
    private final String type;

    @Size(min = 1, max = 10)
    private final String suggestedAmount;

    @Size(min = 1, max = 10)
    private final String suggestedSteepingTime;

    @Size(min = 1, max = 10)
    private final String suggestedWaterTemperature;
}
