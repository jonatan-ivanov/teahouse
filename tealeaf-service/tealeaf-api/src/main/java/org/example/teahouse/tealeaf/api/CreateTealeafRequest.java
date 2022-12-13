package org.example.teahouse.tealeaf.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class CreateTealeafRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private final String name;

    @NotNull
    @Size(min = 1, max = 10)
    private final String type;

    @NotNull
    @Size(min = 1, max = 10)
    private final String suggestedAmount;

    @NotNull
    @Size(min = 1, max = 10)
    private final String suggestedSteepingTime;

    @NotNull
    @Size(min = 1, max = 10)
    private final String suggestedWaterTemperature;
}
